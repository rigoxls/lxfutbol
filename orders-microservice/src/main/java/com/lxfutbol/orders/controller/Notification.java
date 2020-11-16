package com.lxfutbol.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.orders.dto.EmailBodyDto;
import com.lxfutbol.orders.interfaces.INotificationServices;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/notification")
public class Notification {


	@Autowired
	private INotificationServices emailPort;
	
	@PostMapping("/send")
	@ResponseBody
	public boolean SendEmail(@RequestBody EmailBodyDto emailBody)  {
		return emailPort.sendEmail(emailBody);
		
	}
	
}
