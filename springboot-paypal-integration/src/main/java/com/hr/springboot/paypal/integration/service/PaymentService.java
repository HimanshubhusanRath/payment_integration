package com.hr.springboot.paypal.integration.service;

import java.net.URI;

import com.hr.springboot.paypal.integration.dto.CreatedOrderDTO;

public interface PaymentService {
	
	CreatedOrderDTO createOrder(Double totalAmount, URI returnUrl);

	void captureOrder(String orderId);
}
