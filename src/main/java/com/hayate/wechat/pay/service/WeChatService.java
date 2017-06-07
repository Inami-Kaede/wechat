package com.hayate.wechat.pay.service;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;

public interface WeChatService {

	ResponseEntity withdraw(Double amount,String orderId,String account);

	ResponseEntity orderQuery(String orderId);

	ResponseEntity withdrawQuery(String orderId);

	ResponseEntity closeOrder(String orderId);
	
	ResponseEntity order(String orderId,Double totalPrice,DateTime dt);
	
	ResponseEntity notify(HttpServletRequest request);
}
