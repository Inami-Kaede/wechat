package com.hayate.wechat.pay.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.AliPayConfig;
import com.hayate.wechat.common.config.PayStatus;
import com.hayate.wechat.common.pojo.CommonResult;
import com.hayate.wechat.pay.service.AliPayService;


@Service
public class AliPayServiceImpl extends BaseService implements AliPayService {

	@Override
	public CommonResult order(String orderId,Double totalPrice) {
		
		logger.info("开始支付宝下单!订单号："+orderId);
		
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(
				AliPayConfig.GATEWAY_URL, AliPayConfig.APP_ID,
				AliPayConfig.PRIVATE_KEY, AliPayConfig.FORMAT,
				AliPayConfig.CHARSET, AliPayConfig.PUBLIC_KEY,
				AliPayConfig.SIGN_TYPE);
		
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setSubject("支付宝订单-"+orderId);
		model.setOutTradeNo(orderId);
		model.setTimeoutExpress("90m");
		model.setTotalAmount(String.valueOf(totalPrice));
		model.setProductCode("QUICK_MSECURITY_PAY");
		
		// model.setSellerId("18883168398");	//收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
		
		request.setBizModel(model);
		request.setNotifyUrl(AliPayConfig.NOTIFY_URL);
		
		try {
			
			logger.info("---------开始发送数据----------------");
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient
					.sdkExecute(request);
			
			if(response.isSuccess()){
				
				logger.debug(response.getBody());		// 就是orderString
				String body = response.getBody();		// 可以直接给客户端请求，无需再做处理。
				
				if(StringUtils.isEmpty(body)){
					
					return new CommonResult(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR, "返回结果为空");
					
				}
				return new CommonResult(body);
			
			}else{
				
				logger.error("订单号为："+orderId+"的订单（第三方----支付宝）下单失败！错误信息："+response.getCode()+":"+response.getMsg());
				logger.error("详细错误信息："+response.getSubCode()+":"+response.getSubMsg());
				return new CommonResult(response.getSubCode(), response.getSubMsg());
			
			}
		
		} catch (AlipayApiException e) {
			
			logger.error(e.toString());
			return new CommonResult(PayStatus.PAY_ERR_CODE_UNKNOWN_ERROR, "未知错误");
		}
	}
	
	@Override
	public CommonResult notify(HttpServletRequest request) {
		
		logger.info("======支付宝回调==========");
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			
			for (int i = 0; i < values.length; i++) {
				
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			
			}
			
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			
			params.put(name, valueStr);
		}
		
		logger.debug("-----------返回数据-----------------");
		for (Entry<String, String> entry : params.entrySet()) {
			
			logger.debug(entry.getKey() + ":" + entry.getValue());
		
		}		
		logger.debug("-----------返回数据(完)--------------");
		
		// 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		// boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String
		// publicKey, String charset, String sign_type)
		
		try {
			
			boolean flag = AlipaySignature.rsaCheckV1(params,
					AliPayConfig.PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
			
			//验证是否为支付宝的消息
			if (flag) {
				
				String orderId = params.get("out_trade_no");
				String trade_status = params.get("trade_status");
				String stringTotalAmount = params.get("total_amount");
				double totalAmount = Double.parseDouble(stringTotalAmount);
				
				//TODO 查询本地数据库订单状态（注意！！！！！此处为了统一本地订单状态 统一采用了微信的订单状态）
				String oldStatus = "NOTPAY";	//本地订单状态
				Double totalPrice = 233.03;	//本地订单金额
				
				if(!oldStatus.equals(PayStatus.WECHAT_TRADE_STATUS_NOTPAY)){

					logger.info("支付结果通知：（重复的请求）订单号为："+orderId+"的订单已处理");
					return new CommonResult(false, PayStatus.PAY_ERR_CODE_REPETITIVE_SUBMISSION, "重复的请求", PayStatus.ALI_NOTIFY_RETURN_CODE_SUCCESS);
					
				}
				
				
				if(totalPrice == totalAmount){
					
					//如果已支付
					if(trade_status.equals(PayStatus.ALI_TRADE_STATUS_TRADE_SUCCESS) || trade_status.equals(PayStatus.ALI_TRADE_STATUS_TRADE_FINISHED)){
						
						//TODO 返回订单号，用作后续业务处理
						logger.info("订单号："+orderId+"的订单已支付");
						return new CommonResult(orderId);
						
					}else if(trade_status.equals(PayStatus.ALI_TRADE_STATUS_TRADE_CLOSED)){
						
						//TODO !!!!!!!!!!!!!!!!!!!!!此处比较特殊，注意业务处理!!!!!!!!!!!!!!!!!!!!!!
						//如果订单被关闭
						logger.info("订单号："+orderId+"的订单被关闭");
						return new CommonResult(false, PayStatus.PAY_ERR_CODE_ALI_ORDER_CLOSED, "订单被关闭", orderId);
						
					}else{
						
						logger.error("订单状态错误，订单号："+orderId);
						return new CommonResult(false, PayStatus.PAY_ERR_CODE_TRADE_STATUS_ERROR, "订单状态错误", PayStatus.ALI_NOTIFY_RETURN_CODE_FAILURE);
					
					}
									
				}else{
					
					//异步通知的价格和本地价格不一致
					logger.error("价格信息错误，订单号："+orderId);
					return new CommonResult(false, PayStatus.PAY_ERR_CODE_AMOUNT_ERROR, "金额不一致", PayStatus.ALI_NOTIFY_RETURN_CODE_FAILURE);
				
				}
							
			}else{
				
				//签名校验失败
				logger.error("签名校验失败！");
				return new CommonResult(false, PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名校验失败！", PayStatus.ALI_NOTIFY_RETURN_CODE_FAILURE);
				
			}
		
		} catch (AlipayApiException e) {
			
			logger.error(e.toString());
			return new CommonResult(false, PayStatus.PAY_ERR_CODE_UNKNOWN_ERROR, "未知错误", PayStatus.ALI_NOTIFY_RETURN_CODE_FAILURE);
		
		}
	}
	
	@Override
	public CommonResult orderQuery(String orderId) {

		logger.info("支付宝订单查询，订单号为:"+orderId);
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL,AliPayConfig.APP_ID,AliPayConfig.PRIVATE_KEY,AliPayConfig.FORMAT,AliPayConfig.CHARSET,AliPayConfig.PUBLIC_KEY,AliPayConfig.SIGN_TYPE);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(orderId);
		request.setBizModel(model);
		AlipayTradeQueryResponse response;
		
		try {
			response = alipayClient.execute(request);
			
			Map<String, String> params = response.getParams();
			
			logger.debug("-----------返回数据(开始)-----------------");
			logger.debug(response.getBody());
			
			for (Entry<String, String> entry : params.entrySet()) {
				
				logger.debug(entry.getKey() + ":" + entry.getValue());
			
			}		
			logger.debug("-----------返回数据(结束)--------------");
			
			
			if(response.isSuccess()){
				
				String newOrderId = response.getOutTradeNo();
				String newTradeStatus = response.getTradeStatus();
				
				logger.info("订单查询（支付宝）成功！返回的订单号为："+newOrderId+" 当前状态为："+newTradeStatus);
				return new CommonResult(newTradeStatus);
			
			} else {
				
				logger.error("订单号为："+orderId+"的订单（支付宝）查询失败！错误信息："+response.getCode()+":"+response.getMsg());
				logger.error("详细错误信息："+response.getSubCode()+":"+response.getSubMsg());
				return new CommonResult(response.getSubCode(), response.getSubMsg());
			
			}
		
		} catch (AlipayApiException e) {
			
			logger.error(e.toString());
			return new CommonResult(PayStatus.PAY_ERR_CODE_UNKNOWN_ERROR, "未知错误");
		
		}
	}
	
	@Override
	public CommonResult closeOrder(String orderId) {

		logger.info("关闭支付宝订单，订单号为:"+orderId);
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL,AliPayConfig.APP_ID,AliPayConfig.PRIVATE_KEY,AliPayConfig.FORMAT,AliPayConfig.CHARSET,AliPayConfig.PUBLIC_KEY,AliPayConfig.SIGN_TYPE);
		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		AlipayTradeCloseModel model = new AlipayTradeCloseModel();
		model.setOutTradeNo(orderId);
		request.setBizModel(model);
		AlipayTradeCloseResponse response;
		
		try {
			
			response = alipayClient.execute(request);
			
			Map<String, String> params = response.getParams();
			
			logger.debug("-----------返回数据-----------------");
			logger.debug(response.getBody());
			
			for (Entry<String, String> entry : params.entrySet()) {
				
				logger.debug(entry.getKey() + ":" + entry.getValue());
			
			}		
			logger.debug("-----------返回数据(完)--------------");
			
			
			if(response.isSuccess()){
				
				String newOrderId = response.getOutTradeNo();
				
				logger.info("支付宝订单关闭成功！返回订单号为："+newOrderId);
				
				//TODO 返回关闭的订单号，用做后续业务处理
				
				return new CommonResult(newOrderId);
			
			} else {

				logger.error("订单号为："+orderId+"的订单（第三方----支付宝）关闭失败！错误信息："+response.getCode()+":"+response.getMsg());
				logger.error("详细错误信息："+response.getSubCode()+":"+response.getSubMsg());
				return new CommonResult(response.getSubCode(), response.getSubMsg());
			
			}
		
		} catch (AlipayApiException e) {
			
			logger.error(e.toString());
			return new CommonResult(PayStatus.PAY_ERR_CODE_UNKNOWN_ERROR, "未知错误");
		}
		
	}
	
	@Override
	public CommonResult transfer(Double amount,String orderId,String aliAccount) {
				
		logger.info("进行支付宝转账，订单号为:"+orderId);
		//发送请求到支付宝
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL,AliPayConfig.APP_ID,AliPayConfig.PRIVATE_KEY,AliPayConfig.FORMAT,AliPayConfig.CHARSET,AliPayConfig.PUBLIC_KEY,AliPayConfig.SIGN_TYPE);
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		
		AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
		model.setAmount(String.valueOf(amount));
		model.setOutBizNo(orderId);
		model.setPayeeType(AliPayConfig.PAYEE_TYPE_LOGONID);
		model.setPayeeAccount(aliAccount);
		//model.setRemark("备注信息");
		
		request.setBizModel(model);
		AlipayFundTransToaccountTransferResponse response;
		
		try {
			
			response = alipayClient.execute(request);
			
			Map<String, String> params = response.getParams();
			
			logger.debug("-----------返回数据-----------------");
			logger.debug(response.getBody());
			
			for (Entry<String, String> entry : params.entrySet()) {
				
				logger.debug(entry.getKey() + ":" + entry.getValue());
			
			}		
			logger.debug("-----------返回数据(完)--------------");
			
			logger.debug(response.getCode()+response.getMsg()+response.getSubCode()+response.getSubMsg());
			
			if(response.isSuccess()){
				
				logger.info("打款（支付宝）成功!对应订单号："+orderId);
				
				String newOrderId = response.getOutBizNo();	//商户转账唯一订单号：发起转账来源方定义的转账单据号。请求时对应的参数，原样返回。
				String payDate = response.getPayDate();		//	支付时间：格式为yyyy-MM-dd HH:mm:ss，仅转账成功返回。
				String transferId = response.getOrderId(); 	//支付宝转账单据号，成功一定返回，失败可能不返回也可能返回。
				
				if(!StringUtils.isEmpty(transferId) && !StringUtils.isEmpty(payDate)){
					
					logger.info("支付宝转账成功！返回支付宝转账单据号为："+transferId);
					
					//TODO 返回成功信息（日期），做后续业务处理
					
					return new CommonResult(payDate);
				
				}
				
				logger.error("订单号为："+orderId+"的订单（支付宝）返回结果异常！"+response.getCode()+":"+response.getMsg());
				return new CommonResult(response.getCode(), response.getMsg());
			} else {
				
				logger.error("订单号为："+orderId+"的订单（支付宝）打款失败！错误信息："+response.getCode()+":"+response.getMsg());
				logger.error("详细错误信息："+response.getSubCode()+":"+response.getSubMsg());
				return new CommonResult(response.getSubCode(), response.getSubMsg());
			}
		
		} catch (AlipayApiException e) {			
			logger.error(e.toString());
			return new CommonResult(PayStatus.PAY_ERR_CODE_UNKNOWN_ERROR, "未知错误");
		
		}
	}
	
	@Override
	public CommonResult transferQuery(String orderId) {

		logger.info("开始订单号为:"+orderId+"订单的打款（支付宝）查询");
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY_URL,AliPayConfig.APP_ID,AliPayConfig.PRIVATE_KEY,AliPayConfig.FORMAT,AliPayConfig.CHARSET,AliPayConfig.PUBLIC_KEY,AliPayConfig.SIGN_TYPE);
		AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
		AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
		model.setOutBizNo(orderId);
		request.setBizModel(model);
		AlipayFundTransOrderQueryResponse response;
		
		try {
			response = alipayClient.execute(request);
			
			Map<String, String> params = response.getParams();
			
			logger.debug("-----------返回数据-----------------");
			logger.debug(response.getBody());
			
			for (Entry<String, String> entry : params.entrySet()) {
				
				logger.debug(entry.getKey() + ":" + entry.getValue());
			
			}		
			logger.debug("-----------返回数据(完)--------------");
			
			if(response.isSuccess()){
				
				String newOrderId = response.getOutBizNo();	//发起转账来源方定义的转账单据号。 该参数的赋值均以查询结果中 的 out_biz_no 为准。 如果查询失败，不返回该参数
				String newTradeStatus = response.getStatus();	//转账单据状态。
				
				logger.info("打款订单查询（支付宝）成功！返回订单号为："+newOrderId+"的订单当前状态为："+newTradeStatus);
				
				if(newOrderId.equals(orderId) && !StringUtils.isEmpty(newTradeStatus)){
					
					return new CommonResult(newTradeStatus);
				
				}
				logger.error("订单号为："+orderId+"的打款订单查询（支付宝）返回结果异常！"+response.getCode()+":"+response.getMsg());
				return new CommonResult(response.getCode(), response.getMsg());
			
			} else {
								
				logger.error("订单号为："+orderId+"的订单（支付宝）打款查询失败！错误信息："+response.getCode()+":"+response.getMsg());
				logger.error("详细错误信息："+response.getSubCode()+":"+response.getSubMsg());
				return new CommonResult(response.getSubCode(), response.getSubMsg());
			}
		
		} catch (AlipayApiException e) {

			logger.error(e.toString());
			return new CommonResult(PayStatus.PAY_ERR_CODE_UNKNOWN_ERROR, "未知错误");
		}
		
	}
}
