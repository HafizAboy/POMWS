package com.rabbitmq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.model.Order;
import com.rabbitmq.repository.OrderRepository;

/**
 * @author Hafiz
 * @version 0.01
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
	private OrderRepository orderRepo;
	
	@Override
	public Order saveOrder(Order order) throws Exception {
		return orderRepo.save(order);
	}

	@Override
	public List<Order> findAllOrders() {
		return (List<Order>)orderRepo.findAll();
	}

	@Override
	public Order findByOrderNumber(String orderNumber) {
		return orderRepo.findByOrderNumber(orderNumber);
	}

	@Override
	public Order updateOrder(Order order) throws Exception {
		return saveOrder(order);
	}

	@Override
	public Order deleteByOrderNumber(String orderNumber) throws Exception {
		return orderRepo.deleteByOrderNumber(orderNumber);
	}

	@Override
	public void deleteOrder(Order order) throws Exception {
		orderRepo.delete(order);
	}
}
