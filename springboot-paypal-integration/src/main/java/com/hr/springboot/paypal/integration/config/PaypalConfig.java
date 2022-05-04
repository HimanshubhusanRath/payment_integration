package com.hr.springboot.paypal.integration.config;
//package com.hr.springboot.paytm.integration.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.paypal.base.rest.APIContext;
//import com.paypal.base.rest.OAuthTokenCredential;
//import com.paypal.base.rest.PayPalRESTException;
//
//@Configuration
//public class PaypalConfig {
//
//	@Value("${paypal.mode}")
//	private String mode;
//	
//	@Value("${paypal.client.id}")
//	private String clientId;
//	
//	@Value("${paypal.client.secret}")
//	private String clientSecret;
//	
//	@Bean
//	public Map<String, String> paypalSdkConfig()
//	{
//		final Map<String, String> configMap = new HashMap<String, String>();
//		configMap.put("mode", mode);
//		configMap.put("clientId", clientId);
//		configMap.put("clientSecret", clientSecret);
//		return configMap;
//	}
//	
//	
//	@Bean
//	public OAuthTokenCredential oAuthTokenCredential()
//	{
//		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
//	}
//	
//	@Bean
//	public APIContext apiContext() throws PayPalRESTException
//	{
//		return new APIContext(clientId, clientSecret, mode, paypalSdkConfig());
//	}
//}
