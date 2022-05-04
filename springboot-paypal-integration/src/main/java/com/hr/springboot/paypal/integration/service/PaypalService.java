package com.hr.springboot.paypal.integration.service;
//package com.hr.springboot.paytm.integration.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.paypal.api.payments.Amount;
//import com.paypal.api.payments.Payer;
//import com.paypal.api.payments.Payment;
//import com.paypal.api.payments.PaymentExecution;
//import com.paypal.api.payments.RedirectUrls;
//import com.paypal.api.payments.Transaction;
//import com.paypal.base.rest.APIContext;
//import com.paypal.base.rest.PayPalRESTException;
//
//@Service
//public class PaypalService {
//
//	@Autowired
//	private APIContext apiContext;
//	
//	public Payment createPayment(final String currency, final Double amountToBePaid, final String paymentMethod, final String intent, final String returnUrl, final String cancelUrl) throws PayPalRESTException
//	{
//		final Amount amount = new Amount();
//		amount.setCurrency(currency);
//		amount.setTotal(String.valueOf(amountToBePaid));
//		
//		final Transaction transaction = new Transaction();
//		transaction.setAmount(amount);
//		transaction.setDescription("Test Payment");
//		final List<Transaction> transactions = new ArrayList<Transaction>();
//		transactions.add(transaction);
//		
//		final Payer payer = new Payer();
//		payer.setPaymentMethod(paymentMethod);
//		
//		final RedirectUrls redirectUrls = new RedirectUrls();
//		redirectUrls.setCancelUrl(cancelUrl);
//		redirectUrls.setReturnUrl(returnUrl);
//		
//		final Payment payment = new Payment();
//		payment.setPayer(payer);
//		payment.setIntent(intent);
//		payment.setTransactions(transactions);
//		payment.setRedirectUrls(redirectUrls);
//		
//		return payment.create(apiContext);
//	}
//	
//	public Payment executePayment(final String paymentId, final String payerId) throws PayPalRESTException
//	{
//		final Payment payment = new Payment();
//		payment.setId(paymentId);
//		
//		final PaymentExecution paymentExecution = new PaymentExecution();
//		paymentExecution.setPayerId(payerId);
//		
//		return payment.execute(apiContext, paymentExecution);
//	}
//	
//}
