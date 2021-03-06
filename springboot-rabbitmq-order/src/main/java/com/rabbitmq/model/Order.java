package com.rabbitmq.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Hafiz
 * @version 0.01
 */
@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable=false)
    @JsonIgnore
    private UUID id;

    @NotNull(message = "Order number cannot be null")
	@Column(name="order_number")
	private String orderNumber;

    @NotNull(message = "Product code cannot be null")
	@Column(name="product_code")
	private String productCode;

    @NotNull(message = "Price cannot be null")
	@Column(name="price_myr")
	private BigDecimal priceRM;

	@Column(name="price_usd")
	private BigDecimal priceUSD;
    
    @Column(name="quantity")
	private int quantity;

    @NotNull(message = "Status cannot be null")
	@Column(name="status")
	private String status;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public BigDecimal getPriceUSD() {
		return priceUSD;
	}

	public void setPriceUSD(BigDecimal priceUSD) {
		this.priceUSD = priceUSD;
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
