package com.lxfutbol.transformRest.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;

@Configuration
public class TransportClientConfiguration {
	
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

}
