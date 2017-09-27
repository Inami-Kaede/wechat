package com.hayate.wechat.pay.controller;

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
import com.hayate.wechat.common.pojo.CommonResponse;
import com.hayate.wechat.pay.service.AliPayService;

@Api(tags="支付-支付宝")
@Controller
@RequestMapping(value = "pay/ali")
public class AliPayController extends BaseController{

	@Autowired
	private AliPayService aliPayService;
	
	@ApiOperation(value = "支付宝下单") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "totalPrice", value = "订单金额",required = true, dataType = "Double", paramType = "query")
	  })
	@RequestMapping(value="order", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse order(String orderId,Double totalPrice) {
		return aliPayService.order(orderId, totalPrice);
	}
	
	@ApiOperation(value = "支付宝回调") 
	@RequestMapping(value="notify", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse notify(HttpServletRequest request) {
		return aliPayService.notify(request);
	}
	
	@ApiOperation(value = "支付宝订单查询") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="order/query", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse orderQuery(String orderId) {
		return aliPayService.orderQuery(orderId);
	}
	
	@ApiOperation(value = "支付宝关闭订单") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "订单ID",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="order/close", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse closeOrder(String orderId) {
		return aliPayService.closeOrder(orderId);
	}
	
	@ApiOperation(value = "支付宝转账") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "转账订单ID",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "amount", value = "转账订单金额",required = true, dataType = "Double", paramType = "query"),
		@ApiImplicitParam(name = "aliAccount", value = "支付宝账号",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="transfer", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse transfer(Double amount,String orderId,String aliAccount) {
		return aliPayService.transfer(amount, orderId, aliAccount);
	}
	
	@ApiOperation(value = "支付宝转账查询") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId", value = "转账订单ID",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="transfer/query", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse transferQuery(String orderId) {
		return aliPayService.transferQuery(orderId);
	}
}
