package com.rabbitmq.model;

import java.math.BigDecimal;

public class ConversionResponse {
  private String currency;
  private BigDecimal converted;

  public ConversionResponse() {
  }

  public ConversionResponse(String currency, BigDecimal converted) {
    this.currency = currency;
    this.converted = converted;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getConverted() {
    return converted;
  }

  public void setConverted(BigDecimal converted) {
    this.converted = converted;
  }
}
