package com.rabbitmq.client;

import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "springboot-currency-converter", fallback = CurrencyConverterClient.CurrencyConverterClientFallback.class)
public interface CurrencyConverterClient {

	@GetMapping("/convert")
	  public ConverterResponseClient convert(@RequestParam(name = "from", defaultValue = "MYR") String fromCurrency,
	                                    @RequestParam("to") String toCurrency,
	                                    @RequestParam("amount") BigDecimal amount);
	
	@Component
	public static class CurrencyConverterClientFallback implements CurrencyConverterClient {

		@Override
		public ConverterResponseClient convert(String fromCurrency, String toCurrency, BigDecimal amount) {
			return null;
		}
	}
}
