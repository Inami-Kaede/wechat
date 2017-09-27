package com.hayate.wechat.pay.service;

import javax.servlet.http.HttpServletRequest;

import com.hayate.wechat.common.pojo.CommonResponse;


public interface AliPayService {

	CommonResponse order(String orderId, Double totalPrice);

	CommonResponse notify(HttpServletRequest request);

	CommonResponse orderQuery(String orderId);

	CommonResponse closeOrder(String orderId);

	CommonResponse transfer(Double amount, String orderId, String aliAccount);

	CommonResponse transferQuery(String orderId);

}
