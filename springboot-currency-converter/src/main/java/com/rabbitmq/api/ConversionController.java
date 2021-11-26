package com.rabbitmq.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.exceptions.UnknownCurrencyException;
import com.rabbitmq.model.ConversionResponse;

import java.math.BigDecimal;

/**
 * @author Aboy
 * @version 0.01
 */
@CrossOrigin
@RestController
@RequestMapping
public class ConversionController {

  @GetMapping("/convert")
  public ConversionResponse convert(@RequestParam(name = "from", defaultValue = "MYR") String fromCurrency,
                                    @RequestParam(name = "to", defaultValue = "USD") String toCurrency,
                                    @RequestParam("amount") BigDecimal amount) {
    if (!fromCurrency.equalsIgnoreCase("MYR")) {
      throw new UnknownCurrencyException("Unknown 'from' currency: " + fromCurrency + ", only MYR supported.");
    }
    BigDecimal factor = conversionFactorFor(toCurrency);
    return new ConversionResponse(toCurrency, factor.multiply(amount));
  }


  private BigDecimal conversionFactorFor(String toCurrency) {
    BigDecimal factor;
    if (toCurrency.equalsIgnoreCase("USD")) {
      factor = BigDecimal.valueOf(0.24);
    } else {
      throw new UnknownCurrencyException("Unknown 'to' currency: " + toCurrency + ". Only MYR supported");
    }
    return factor;
  }
}
