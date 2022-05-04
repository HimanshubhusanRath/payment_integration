package com.hr.razorpay.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Value("${razorpay.client-id}")
	private String razorPayClientId;
	
	@Value("${razorpay.client-secret}")
	private String razorPayClientSecret;
		
	// Creating order for payment
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String, Object> data) throws Exception
	{
		System.out.println("Request for create-order : "+ data);
		int amt=Integer.parseInt(data.get("amount").toString());

		var razorpayClient=new RazorpayClient(razorPayClientId, razorPayClientSecret);
		
		JSONObject request=new JSONObject();
		request.put("amount", amt);
		request.put("currency", "INR");
		request.put("receipt", "txn_"+System.currentTimeMillis());
		
		// Creating new order
		Order order = razorpayClient.Orders.create(request);
		System.out.println(order);
		
		return order.toString();
	}
	
	@PostMapping("/payment-status")
	public String updatePaymentStatus(
			@RequestParam("razorpay_payment_id") final String paymentId,
			@RequestParam("razorpay_order_id") final String paymentOrderId,
			@RequestParam("razorpay_signature") final String signature,
			@RequestParam(name="error", required = false) final Object[] error
					) throws Exception
	{
		System.out.println("Payment response from Gateway >> "+signature);
		// Do some processing, if required
		final String orderStatus = null!=error ? "FAIL" : "SUCCESSFUL";
		return "redirect:/payment/order-status?orderStatus="+orderStatus;
	}

	@GetMapping("/order-status")
	public String getOrderStatus(Model model, @RequestParam("orderStatus") final String orderStatus) {
		model.addAttribute("orderStatus", orderStatus);
		return "order_status";
	}
}
