package com.hayate.wechat.pay.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hayate.wechat.common.base.BaseController;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.pojo.CommonResponse;
import com.hayate.wechat.pay.service.WeChatPayService;

@Api(tags="支付-微信支付")
@Controller
@RequestMapping(value = "pay/wechat")
public class WeChatPayController extends BaseController{

	@Autowired
	private WeChatPayService weChatPayService;
	
	@ApiOperation(value = "微信统一下单") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "totalPrice", value = "订单金额",required = true, dataType = "Double", paramType = "query"),
		@ApiImplicitParam(name = "tradeType", value = "订单类型",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "openId", value = "用户openid",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="order", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse unifiedOrder(String orderId,Double totalPrice,int tradeType,String openId) {
		return weChatPayService.unifiedOrder(orderId, totalPrice,new Date(), tradeType, openId);
	}
	
	@ApiOperation(value = "微信统一下单-jsapi") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "totalPrice", value = "订单金额",required = true, dataType = "Double", paramType = "query"),
		@ApiImplicitParam(name = "openId", value = "用户openid",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="order/jsapi", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse unifiedOrderJsapi(String orderId,Double totalPrice,String openId) {
		return weChatPayService.unifiedOrderJsapi(orderId, totalPrice, new Date(), openId);
	}
	
	@ApiOperation(value = "微信统一下单-native") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "totalPrice", value = "订单金额",required = true, dataType = "Double", paramType = "query")
	  })
	@RequestMapping(value="order/native", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse unifiedOrderNative(String orderId,Double totalPrice) {
		return weChatPayService.unifiedOrderNative(orderId, totalPrice, new Date());
	}
	
	@ApiOperation(value = "微信统一下单-app") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "totalPrice", value = "订单金额",required = true, dataType = "Double", paramType = "query")
	  })
	@RequestMapping(value="order/app", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse unifiedOrderApp(String orderId,Double totalPrice) {
		return weChatPayService.unifiedOrderApp(orderId, totalPrice, new Date());
	}
	
	@ApiOperation(value = "微信回调") 
	@RequestMapping(value="notify", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse notify(HttpServletRequest request) {
		return weChatPayService.notify(request);
	}
	
	@ApiOperation(value = "微信订单查询") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "orderIdType", value = "订单ID类型",required = true, dataType = "Integer", paramType = "query")
	  })
	@RequestMapping(value="order/query", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse orderQuery(String orderId,int orderIdType) {
		return weChatPayService.orderQuery(orderId, orderIdType);
	}
	
	@ApiOperation(value = "微信订单查询-本地单号") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="order/query/outtradeno", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse orderQueryByOutTradeNo(String orderId) {
		return weChatPayService.orderQueryByOutTradeNo(orderId);
	}
	
	@ApiOperation(value = "微信订单查询-微信单号") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="order/query/transactionid", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse orderQueryByTransactionId(String orderId) {
		return weChatPayService.orderQueryByTransactionId(orderId);
	}
	
	@ApiOperation(value = "微信关闭订单") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="order/close", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse closeOrder(String orderId) {
		return weChatPayService.closeOrder(orderId);
	}
	
	@ApiOperation(value = "微信转账") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "转账订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "amount", value = "转账订单金额",required = true, dataType = "Double", paramType = "query"),
		@ApiImplicitParam(name = "openId", value = "用户openid",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="transfers", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse transfers(Double amount,String orderId,String openId) {
		return weChatPayService.transfers(amount, orderId, openId);
	}
	
	@ApiOperation(value = "微信转账查询") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "转账订单ID",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="transfers/query", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse transfersQuery(String orderId) {
		return weChatPayService.transfersQuery(orderId);
	}
}
