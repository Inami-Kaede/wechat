package com.hayate.wechat.pay.service;

import javax.servlet.http.HttpServletRequest;

import com.hayate.wechat.common.pojo.CommonResult;


public interface AliPayService {

	CommonResult order(String orderId, Double totalPrice);

	CommonResult notify(HttpServletRequest request);

	CommonResult orderQuery(String orderId);

	CommonResult closeOrder(String orderId);

	CommonResult transfer(Double amount, String orderId, String aliAccount);

	CommonResult transferQuery(String orderId);

}
