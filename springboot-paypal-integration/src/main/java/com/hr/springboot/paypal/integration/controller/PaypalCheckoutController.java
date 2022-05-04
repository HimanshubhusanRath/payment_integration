package com.hr.springboot.paypal.integration.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hr.springboot.paypal.integration.dto.CreatedOrderDTO;
import com.hr.springboot.paypal.integration.service.PaymentService;

@Controller
@RequestMapping("/orders")
public class PaypalCheckoutController {

	@Autowired
	private PaymentService paymentService;
	
	private String orderId;

	@GetMapping
	public String ordersHomePage(final Model model) {
		model.addAttribute("orderId", orderId);
		return "orders-home";
	}

	@PostMapping("/create")
	public String placeOrder(@RequestParam final Double amountToBePaid, final HttpServletRequest request)
	{
		final URI returnUrl = buildReturnUrl(request);
		final CreatedOrderDTO createdOrderDto = paymentService.createOrder(amountToBePaid, returnUrl);
		return "redirect:"+createdOrderDto.getApprovalLink();
	}

	@GetMapping("/capture")
	public String captureOrder(@RequestParam final String token)
	{
		orderId = token;
		paymentService.captureOrder(token);
		return "redirect:/orders";
	}
	
	
	private URI buildReturnUrl(HttpServletRequest request) {
		final URI requestUri = URI.create(request.getRequestURL().toString());
		try {
			return new URI(
					requestUri.getScheme(),
					requestUri.getUserInfo(),
					requestUri.getHost(),
					requestUri.getPort(),
					"/orders/capture",
					null,
					null
					);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
