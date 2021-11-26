package com.rabbitmq.client;

import java.math.BigDecimal;

public class ConverterResponseClient {
  private String currency;
  private BigDecimal converted;

  public ConverterResponseClient() {
  }

  public ConverterResponseClient(String currency, BigDecimal converted) {
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
