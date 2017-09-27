package com.hayate.wechat.pay.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.hayate.wechat.common.pojo.CommonResponse;


public interface WeChatPayService {

	CommonResponse unifiedOrder(String orderId, Double totalPrice, Date date,
			int tradeType, String openId);

	CommonResponse unifiedOrderJsapi(String orderId, Double totalPrice,
			Date date, String openId);

	CommonResponse unifiedOrderNative(String orderId, Double totalPrice, Date date);

	CommonResponse unifiedOrderApp(String orderId, Double totalPrice, Date date);

	CommonResponse notify(HttpServletRequest request);

	CommonResponse orderQuery(String orderId, int orderIdType);

	CommonResponse orderQueryByOutTradeNo(String orderId);

	CommonResponse orderQueryByTransactionId(String orderId);

	CommonResponse closeOrder(String orderId);

	CommonResponse transfers(Double amount, String orderId, String openId);

	CommonResponse transfersQuery(String orderId);

}
