package com.lxfutbol.orders.interfaces;

import com.lxfutbol.orders.dto.EmailBodyDto;

public interface INotificationServices {

	public boolean sendEmail(EmailBodyDto emailBody);
	
}
