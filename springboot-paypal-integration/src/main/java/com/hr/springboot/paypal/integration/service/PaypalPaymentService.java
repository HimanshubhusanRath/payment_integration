package com.hr.springboot.paypal.integration.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hr.springboot.paypal.integration.dto.CreatedOrderDTO;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaypalPaymentService implements PaymentService {

	private PayPalHttpClient paypalHttpClient;

	private final static String INTENT_CAPTURE = "CAPTURE";
	private final static String APPROVAL_STATUS = "approve";

	public PaypalPaymentService(@Value("${paypal.clientId}") final String clientId,
			@Value("${paypal.clientSecret}") final String clientSecret) {
		paypalHttpClient = new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
	}

	@Override
	@SneakyThrows
	public CreatedOrderDTO createOrder(Double totalAmount, URI returnUrl) {
		final OrderRequest orderRequest = createOrderRequest(totalAmount, returnUrl);
		final OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);
		final HttpResponse<Order> orderHttpResponse = paypalHttpClient.execute(ordersCreateRequest);
		final Order order = orderHttpResponse.result();
		final LinkDescription approveUri = getApprovalLink(order);
		return new CreatedOrderDTO(order.id(), URI.create(approveUri.href()));
	}
	
	@Override
	@SneakyThrows
	public void captureOrder(String orderId) {
		final OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
		final HttpResponse<Order> captureResponse = paypalHttpClient.execute(ordersCaptureRequest);
		log.info("Capture status for the order >> {}",captureResponse.result().status());
	}

	private LinkDescription getApprovalLink(Order order) {
		return order.links().stream().filter(l -> APPROVAL_STATUS.equals(l.rel())).findFirst()
				.orElseThrow(NoSuchElementException::new);
	}

	private OrderRequest createOrderRequest(Double totalAmount, URI returnUrl) {
		final OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent(INTENT_CAPTURE);
		orderRequest.purchaseUnits(getPurchaseUnits(totalAmount));
		orderRequest.applicationContext(new ApplicationContext().returnUrl(returnUrl.toString()));
		return orderRequest;
	}

	private List<PurchaseUnitRequest> getPurchaseUnits(Double totalAmount) {
		final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(String.valueOf(totalAmount)));
		return Arrays.asList(purchaseUnitRequest);

	}

}
