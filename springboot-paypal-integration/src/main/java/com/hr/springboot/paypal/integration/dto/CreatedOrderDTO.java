package com.hr.springboot.paypal.integration.dto;

import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedOrderDTO {

	private String orderId;
	private URI approvalLink;
	
}
