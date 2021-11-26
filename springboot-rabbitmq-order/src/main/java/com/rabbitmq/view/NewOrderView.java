package com.rabbitmq.view;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Hafiz
 * @version 0.01
 */
public class NewOrderView {

    @JsonIgnore
    private UUID id;

    @NotNull(message = "Order number cannot be null")
	private String orderNumber;

    @NotNull(message = "Product code cannot be null")
	private String productCode;

    @NotNull(message = "Price cannot be null")
	private BigDecimal priceRM;

	private int quantity;

    @NotNull(message = "Status cannot be null")
	private String status;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getPriceRM() {
		return priceRM;
	}

	public void setPriceRM(BigDecimal priceRM) {
		this.priceRM = priceRM;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
