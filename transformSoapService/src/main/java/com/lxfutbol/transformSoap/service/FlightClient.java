package com.lxfutbol.transformSoap.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxfutbol.transformSoap.dto.Contractdto;

@FeignClient(name="contract", url="http://localhost:8088/")
public interface FlightClient {
	@RequestMapping(method = RequestMethod.GET, value = "/contract")
	 public Contractdto getContract();
}
