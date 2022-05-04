package com.hr.springboot.paypal.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

	private String currency;
	private Double amountToBePaid;
	private String intent;
	private String description;
	private String method;
	
}
