package com.hr.springboot.paypal.integration.controller;
//package com.hr.springboot.paytm.integration.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.hr.springboot.paytm.integration.dto.PaymentDTO;
//import com.hr.springboot.paytm.integration.service.PaypalService;
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//
//public class PaypalController {
//
//	@Value("${server.port}")
//	private String port;
//
//	private final String HOST = "http://localhost:" + port;
//	private final String SUCCESS_URL = "/pay/success";
//	private final String CANCEL_URL = "/pay/cancel";
//
//	@Autowired
//	private PaypalService paypalService;
//
//	@GetMapping("/")
//	public String home() {
//		return "home";
//	}
//
//	@PostMapping("/pay")
//	public String payment(@ModelAttribute("payment") final PaymentDTO paymentDTO) {
//		try {
//			final Payment payment = paypalService.createPayment(paymentDTO.getCurrency(),
//					paymentDTO.getAmountToBePaid(), paymentDTO.getMethod(), paymentDTO.getIntent(),
//					HOST + SUCCESS_URL, HOST + CANCEL_URL);
//			
//			// If the request is approved, then redirect to the paypal url
//			for(Links link:payment.getLinks())
//			{
//				if(link.getRel().equals("approval_url"))
//				{
//					return "redirect:"+link.getHref();
//				}
//			}
//
//		} catch (Exception e) {
//			System.out.println("Exception in payment > ");
//			e.printStackTrace();
//		}
//
//		return "redirect:/";
//	}
//	
//	@GetMapping(value = SUCCESS_URL)
//	public String paymentSuccess(@RequestParam("payerId") final String payerId, @RequestParam("paymentId") final String paymentId)
//	{
//		try
//		{
//			final Payment payment = paypalService.executePayment(paymentId, payerId);
//			System.out.println("Payment Response >> \n ------------------\n"+payment.toJSON());
//			if(payment.getState().equals("approved"))
//			{
//				return "success-page";
//			}
//		}
//		catch(Exception e)
//		{
//			System.out.println("Error in success payment response > "+e);
//			e.printStackTrace();
//		}
//		
//		return "redirect:/";
//	}
//	
//	@GetMapping(value=CANCEL_URL)
//	public String paymentCancel()
//	{
//		return "cancel-page";
//	}
//	
//	
//	
//}
