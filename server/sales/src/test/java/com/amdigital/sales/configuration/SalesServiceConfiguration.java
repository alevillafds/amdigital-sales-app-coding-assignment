package com.amdigital.sales.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amdigital.sales.service.SalesService;

@Configuration
public class SalesServiceConfiguration {
	
	@Bean
	SalesService salesService() {
		return new SalesService();
	}

}
