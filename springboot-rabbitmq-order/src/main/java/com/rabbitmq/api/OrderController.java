package com.rabbitmq.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.businessrule.ProductOrder;
import com.rabbitmq.client.ConverterResponseClient;
import com.rabbitmq.client.CurrencyConverterClient;
import com.rabbitmq.config.MQConfig;
import com.rabbitmq.constants.ErrorEnum;
import com.rabbitmq.exceptions.WebserviceException;
import com.rabbitmq.model.Order;
import com.rabbitmq.service.OrderService;
import com.rabbitmq.view.NewOrderView;
import com.rabbitmq.view.UpdateOrderView;

import io.swagger.annotations.ApiOperation;

/**
 * @author Aboy
 * @version 0.01
 */
@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderService orderService;

    @Autowired
    private RabbitTemplate template;
    
    @Autowired
    CurrencyConverterClient ccClient;
    
	/**
	 * Fetch a list of orders
	 * @return a list of orders
	 * @throws Exception 
	 */
	@RequestMapping(path="", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Fetch all orders")
	public ResponseEntity<?> orders() throws Exception {
		List<Order> orders = (List<Order>) orderService.findAllOrders();

		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	/**
	 * Finds a order by <code>orderNumber</code>
	 * 
	 * @param orderNumber order's orderNumber
	 * 
	 * @return the {@link Order} object
	 * @throws Exception 
	 */
	@RequestMapping(path = "/getOrderByOrderNumber/{orderNumber}", 
			method = RequestMethod.GET)
	@ApiOperation(value = "Fetch a order")
	public ResponseEntity<?> order(@PathVariable String orderNumber) throws Exception {
		Order order = new Order();
		order = orderService.findByOrderNumber(orderNumber);
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	/**
	 * Add a order
	 * 
	 * @param newOrderView
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = "",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create a new order")
	public ResponseEntity<Order> addOrder(@RequestBody NewOrderView newOrderView) throws Exception {

		Order order = new Order();
		Order savedOrder = new Order();

		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<NewOrderView>> violations = validator.validate(newOrderView);

		logger.error("size of violations : " + violations.size());

		for (ConstraintViolation<NewOrderView> constraintViolation : violations) {
			logger.error("constraintViolation: field \"" + constraintViolation.getPropertyPath() + "\"," + constraintViolation.getMessage());
			throw new WebserviceException(ErrorEnum.REQUIRED_ELEMENT_MISSING, constraintViolation.getMessage());
		}
		// validation - End

		if(violations.size() == 0) {
			logger.info("Validation Success!");

			//OpenFeign for currency conversion
			ConverterResponseClient crClient = ccClient.convert("MYR", "USD", newOrderView.getPriceRM());
			
			BigDecimal curUsd = crClient.getConverted();
			logger.info("Currency in MYR:- "+newOrderView.getPriceRM()+"; currency in USD:- "+curUsd);
			
			//set view to model to be saved
			order.setOrderNumber(newOrderView.getOrderNumber());
			order.setProductCode(newOrderView.getProductCode());
			order.setPriceRM(newOrderView.getPriceRM());
			order.setPriceUSD(curUsd);
			order.setQuantity(newOrderView.getQuantity());
			order.setStatus(newOrderView.getStatus());
			
			try {
				logger.info("Saving Order");
				savedOrder =  orderService.saveOrder(order);
				logger.info("Order creation completed");
			}catch (Exception e) {
				logger.error("Error:- Unable to create order");
				throw new WebserviceException(ErrorEnum.SAVING_UNSUCCESSFUL, ErrorEnum.SAVING_UNSUCCESSFUL.getDescription());
			}
		}
		return new ResponseEntity<Order>(savedOrder, HttpStatus.CREATED);
	}

	/**
	 * Updates the order
	 * 
	 * @param updateOrderView
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = "/{orderNumber}",
			method = RequestMethod.PUT)
	@ApiOperation(value = "Update a order")
	public ResponseEntity<Order> updateOrder(@PathVariable String orderNumber, @RequestBody UpdateOrderView updateOrderView) throws Exception {

		Order savedOrder = new Order();
		logger.info("Order no: "+orderNumber);
		Order orderFindByOrderNumber = orderService.findByOrderNumber(orderNumber);
		logger.info("Selected prod code: "+orderFindByOrderNumber.getProductCode());
		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UpdateOrderView>> violations = validator.validate(updateOrderView);

		logger.error("size of violations : " + violations.size());

		for (ConstraintViolation<UpdateOrderView> constraintViolation : violations) {
			logger.error("constraintViolation: field \"" + constraintViolation.getPropertyPath() + "\"," + constraintViolation.getMessage());
			throw new WebserviceException(ErrorEnum.REQUIRED_ELEMENT_MISSING, constraintViolation.getMessage());
		}
		// validation - End

		if(violations.size() == 0) {
			logger.info("Validation Success!");

			//OpenFeign for currency conversion
			ConverterResponseClient crClient = ccClient.convert("MYR", "USD", updateOrderView.getPriceRM());
			
			BigDecimal curUsd = crClient.getConverted();
			logger.info("Currency in MYR:- "+updateOrderView.getPriceRM()+"; currency in USD:- "+curUsd);
			
			logger.info("Selected Order no: "+orderFindByOrderNumber.getOrderNumber());
			orderFindByOrderNumber.setProductCode(updateOrderView.getProductCode());
			orderFindByOrderNumber.setPriceRM(updateOrderView.getPriceRM());
			orderFindByOrderNumber.setPriceUSD(curUsd);
			orderFindByOrderNumber.setQuantity(updateOrderView.getQuantity());
			orderFindByOrderNumber.setStatus(updateOrderView.getStatus());

			//RabbitMQ send to other webservice
			ProductOrder prodOrder = new ProductOrder();
			prodOrder.setProductCode(updateOrderView.getProductCode());
			prodOrder.setStock(updateOrderView.getQuantity());
			template.convertAndSend(MQConfig.EXCHANGE,
	                MQConfig.ROUTING_KEY, prodOrder);
			try {
				logger.info("Update Order");
				savedOrder = orderService.updateOrder(orderFindByOrderNumber);
				logger.info("Order updated");
			}catch (Exception e) {
				logger.error("Error:- Unable to update order");
				throw new WebserviceException(ErrorEnum.SAVING_UNSUCCESSFUL, ErrorEnum.SAVING_UNSUCCESSFUL.getDescription());
			}
		}
		
		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);
	}


	/**
	 * Deletes order identified with <code>orderNumber</code>
	 * @param orderNumber
	 * @throws Exception 
	 */
	@RequestMapping(path = "/{orderNumber}", 
			method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a order")
	public ResponseEntity<?> deleteOrder(@PathVariable String orderNumber) throws Exception {

		Order orderFindByOrderNumber = orderService.findByOrderNumber(orderNumber);
		try {
			logger.info("Delete Order");
			orderService.deleteOrder(orderFindByOrderNumber);
			logger.info("Order Deleted");
		}catch (Exception e) {
			logger.error("Error:- Unable to delete order");
			throw new WebserviceException(ErrorEnum.DELETION_UNSUCCESSFUL, ErrorEnum.DELETION_UNSUCCESSFUL.getDescription());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
