package com.hayate.wechat.pay.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.hayate.wechat.common.pojo.CommonResult;


public interface WeChatPayService {

	CommonResult unifiedOrder(String orderId, Double totalPrice, Date date,
			int tradeType, String openId);

	CommonResult unifiedOrderJsapi(String orderId, Double totalPrice,
			Date date, String openId);

	CommonResult unifiedOrderNative(String orderId, Double totalPrice, Date date);

	CommonResult unifiedOrderApp(String orderId, Double totalPrice, Date date);

	CommonResult notify(HttpServletRequest request);

	CommonResult orderQuery(String orderId, int orderIdType);

	CommonResult orderQueryByOutTradeNo(String orderId);

	CommonResult orderQueryByTransactionId(String orderId);

}
