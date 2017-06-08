package com.hayate.wechat.pay.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.PayStatus;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.pojo.CommonResult;
import com.hayate.wechat.common.util.Arith;
import com.hayate.wechat.common.util.CommonUtil;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.common.util.NetUtil;
import com.hayate.wechat.pay.service.WeChatPayService;



public class WeChatPayServiceImpl extends BaseService implements WeChatPayService{

	@Override
	public CommonResult unifiedOrder(String orderId,Double totalPrice,Date date,int tradeType,String openId) {
		
		DateTime dt = new DateTime(date);
		
		//填充数据
		SortedMap<Object,Object> sm = new TreeMap<Object,Object>();
		sm.put("appid", WeChatOaConfig.APP_ID);
		sm.put("mch_id", WeChatOaConfig.MCHID);
		sm.put("nonce_str", CommonUtil.CreateNoncestr());
		sm.put("body", "商户订单号-"+orderId);
		sm.put("out_trade_no", orderId);
		sm.put("total_fee", String.valueOf((int)(Arith.mul(totalPrice, 100))));
		sm.put("spbill_create_ip",NetUtil.getLocalIp());
		sm.put("time_start",dt.toString("yyyyMMddHHmmss"));
		sm.put("time_expire",dt.plusMinutes(WeChatOaConfig.ORDER_EXPIRE).toString("yyyyMMddHHmmss"));
		sm.put("notify_url", WeChatOaConfig.NOTIFY_URL);
		
		if(tradeType == WeChatOaConfig.INT_TRADE_TYPE_JSAPI){
			sm.put("trade_type", WeChatOaConfig.TRADE_TYPE_JSAPI);	
			sm.put("openid", openId);	
		
		}else if(tradeType == WeChatOaConfig.INT_TRADE_TYPE_NATIVE){
			sm.put("trade_type", WeChatOaConfig.TRADE_TYPE_NATIVE);	
			//parameters.put("product_id", orderId);	//测试后发现可有可无
		
		}else if(tradeType == WeChatOaConfig.INT_TRADE_TYPE_APP){
			sm.put("trade_type", WeChatOaConfig.TRADE_TYPE_APP);	
		}
		
		String sign = CommonUtil.createSign(WeChatOaConfig.CHARSET, sm,WeChatOaConfig.KEY);
		sm.put("sign", sign);
		
		//转换成xml
		String requestXml = CommonUtil.getRequestXml(sm);
		logger.debug("微信统一下单发送的XML："+requestXml);
		
		//发送到微信服务器并拿到返回的xml
		String xmlResult = HttpClientUtil.doPostXml(
				WeChatOaConfig.PAY_UNIFIED_ORDER, requestXml);
		
		logger.debug("微信统一下单返回的XML："+xmlResult);
		
		if (StringUtils.isEmpty(xmlResult)){
			
			logger.error("错误：下单（微信）失败！请求未响应或为空！对应订单号为："+orderId);
			return new CommonResult(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR,"请求未响应或为空");
		
		}
		
		//包装返回到手机的数据
		Map<String, Object> mapResult = CommonUtil.parseXml(xmlResult);
		
		// 判断通信是否成功
		if (mapResult.get("return_code").equals(PayStatus.WECHAT_RETURN_CODE_SUCCESS)) {
			
			// 判断业务是否成功
			if (mapResult.get("result_code").equals(PayStatus.WECHAT_RESULT_CODE_SUCCESS)) {
				
				// 验证是否是微信服务器的信息
				String sign2 = (String) mapResult.get("sign");
				mapResult.remove("sign");
				SortedMap<Object,Object> sm2 = new TreeMap<Object,Object>(mapResult);							
				
				if (sign2.equals(CommonUtil.createSign(WeChatOaConfig.CHARSET, sm2,WeChatOaConfig.KEY))) {
					
					String prepayId = (String) mapResult.get("prepay_id");
					SortedMap<Object,Object> sm3 = new TreeMap<Object,Object>();
					
					
					if(tradeType == WeChatOaConfig.INT_TRADE_TYPE_JSAPI){
						
						sm3.put("appId", WeChatOaConfig.APP_ID);
						sm3.put("timeStamp", dt.toString("yyMMddHHmm"));
						sm3.put("nonceStr", CommonUtil.CreateNoncestr());
						sm3.put("package", "prepay_id="+prepayId);
						sm3.put("signType", WeChatOaConfig.SIGN_TYPE);
						sm3.put("paySign", CommonUtil.createSign(WeChatOaConfig.CHARSET, sm3,WeChatOaConfig.KEY));
						
					}else if(tradeType == WeChatOaConfig.INT_TRADE_TYPE_NATIVE){
						
						String codeUrl = (String) mapResult.get("code_url");
						return new CommonResult(codeUrl);
						
					}else if(tradeType == WeChatOaConfig.INT_TRADE_TYPE_APP){
						sm3.put("appid", WeChatOaConfig.APP_ID);
						sm3.put("partnerid", WeChatOaConfig.MCHID);
						sm3.put("prepayid", prepayId);
						sm3.put("package", "Sign=WXPay");
						sm3.put("noncestr", CommonUtil.CreateNoncestr());
						sm3.put("timestamp", dt.toString("yyMMddHHmm"));
						sm3.put("sign", CommonUtil.createSign(WeChatOaConfig.CHARSET, sm3,WeChatOaConfig.KEY));
					}
					
					logger.info("微信下单成功!对应订单号为："+orderId+"订单数据: "+requestXml);
					logger.debug("返回数据："+JsonUtils.objectToJson(sm3));
					
					return new CommonResult(sm3);		
					
				}
				
				// 签名错误
				logger.error("统一下单：签名错误！（微信）对应订单号："+orderId);
				return new CommonResult(PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名错误");	
			
			} 
			
			// 业务码错误
			logger.error("统一下单：业务码错误（微信）！对应订单号："+orderId+"错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));			
			return new CommonResult((String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"));
		}
		// 通信码错误
		logger.error("统一下单：通信错误（微信）！错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResult((String)mapResult.get("return_code"), (String)mapResult.get("return_msg"));
	}
	
	@Override
	public CommonResult unifiedOrderJsapi(String orderId,Double totalPrice,Date date,String openId) {
		return unifiedOrder(orderId, totalPrice, date, WeChatOaConfig.INT_TRADE_TYPE_JSAPI, openId);
	}
	
	@Override
	public CommonResult unifiedOrderNative(String orderId,Double totalPrice,Date date) {
		return unifiedOrder(orderId, totalPrice, date, WeChatOaConfig.INT_TRADE_TYPE_NATIVE, null);
	}
	
	@Override
	public CommonResult unifiedOrderApp(String orderId,Double totalPrice,Date date) {
		return unifiedOrder(orderId, totalPrice, date, WeChatOaConfig.INT_TRADE_TYPE_APP, null);
	}
	
	@Override
	public CommonResult notify(HttpServletRequest request) {
		
		Map<String, Object> mapResult = CommonUtil.parseXml(request);
		
		for (Entry<String, Object> entry : mapResult.entrySet()) {
			
			logger.debug(entry.getKey() + " = " + entry.getValue());
		
		}	
			
		// 判断通信是否成功
		if (mapResult.get("return_code").equals(PayStatus.WECHAT_RETURN_CODE_SUCCESS)) {
			
			// 判断业务是否成功
			if (mapResult.get("result_code").equals(PayStatus.WECHAT_RESULT_CODE_SUCCESS)) {
				
				String sign = (String) mapResult.get("sign");
				mapResult.remove("sign");
				SortedMap<Object,Object> sm = new TreeMap<Object,Object>(mapResult);
							
				// 验证是否是微信服务器的信息
				if (sign.equals(CommonUtil.createSign(WeChatOaConfig.CHARSET, sm,WeChatOaConfig.KEY))) {
					
					//本地订单号
					String orderId = (String) mapResult.get("out_trade_no");
									
					String totalFee = (String)mapResult.get("total_fee");		
					
					//微信支付订单号(根据需求决定是否保存)
					String transactionId = (String)mapResult.get("transaction_id");
					
					//TODO 根据订单号查询本地数据库					
					Double payAmount = 233.05; //本地订单金额
					Integer userId = 0;		//下单用户ID
					String orderStatus = PayStatus.WECHAT_TRADE_STATUS_NOTPAY;	//本地数据库订单状态
					
					//判断金额是否一致
					if (!String.valueOf(Arith.mul(payAmount, 100)).equals(totalFee)){
						
						logger.error("本地金额与订单金额信息不匹配！（微信）本地金额："+payAmount+"订单金额："+Double.parseDouble(totalFee)/100);
						return new CommonResult(false, PayStatus.PAY_ERR_CODE_AMOUNT_ERROR, "金额不一致", CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, "金额不一致"));
					}
					
					//判断是否已处理
					if (!orderStatus.equals(PayStatus.WECHAT_TRADE_STATUS_NOTPAY)){
						
						logger.info("（微信）支付结果通知：订单号为："+orderId+"的订单已处理,该信息为重复的通知");
						return new CommonResult(false, PayStatus.PAY_ERR_CODE_REPETITIVE_SUBMISSION, "重复的通知",CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_SUCCESS, "成功"));
					} 												
					
					
					logger.info("订单（微信）已成功支付！订单号为："+orderId+"对应用户ID为："+userId);					
					//TODO 返回成功订单的ID 用作后续订单处理
					return new CommonResult(orderId);
				}
				
				// 签名错误
				logger.error("（微信）支付结果通知：签名错误！");
				return new CommonResult(false, PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名错误", CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, "签名错误"));
			} 
			
			// 业务码错误
			logger.error("（微信）支付结果通知：业务码错误！错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));
			return new CommonResult(false, (String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("err_code_des")));
					
		} 
		
		// 通信码错误
		logger.error("（微信）支付结果通知：通信错误！错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResult(false, (String)mapResult.get("return_code"), (String)mapResult.get("return_msg"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("return_msg")));
	}
	
	/* 
	 * 订单查询（查询统一下单接口的订单）
	 */
	@Override
	public CommonResult orderQuery(String orderId,int orderIdType) {
				
		//准备数据
		SortedMap<Object, Object> sm = new TreeMap<Object, Object>();
		sm.put("appid", WeChatOaConfig.APP_ID);
		sm.put("mch_id", WeChatOaConfig.MCHID);
		if(orderIdType == WeChatOaConfig.ORDER_QUERY_TYPE_OUT_TRADE_NO){
			sm.put("out_trade_no", orderId);			
		}else if(orderIdType == WeChatOaConfig.ORDER_QUERY_TYPE_TRANSACTION_ID){
			sm.put("transaction_id", orderId);
		}
		sm.put("nonce_str", CommonUtil.CreateNoncestr());
		String sign = CommonUtil.createSign(WeChatOaConfig.CHARSET, sm,WeChatOaConfig.KEY);
		sm.put("sign", sign);
		
		String requestXml = CommonUtil.getRequestXml(sm);
		logger.debug("订单查询（微信）-发送数据："+requestXml);
		
		//发送数据
		String xmlResult = HttpClientUtil.doPostXml(WeChatOaConfig.PAY_ORDER_QUERY, requestXml);
		
		if(StringUtils.isEmpty(xmlResult)){
			
			logger.error("订单查询（微信）失败！返回值为空 对应单号为："+orderId);
			return new CommonResult(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR,"请求未响应或为空");
		
		}
		
		Map<String, Object> mapResult = CommonUtil.parseXml(xmlResult);
		
		for(Entry<String, Object> e : mapResult.entrySet()){
			
			logger.debug(e.getKey()+"="+e.getValue());
		
		}
		
		// 判断通信是否成功
		if (mapResult.get("return_code").equals(PayStatus.WECHAT_RETURN_CODE_SUCCESS)) {
			
			// 判断业务是否成功
			if (mapResult.get("result_code").equals(PayStatus.WECHAT_RESULT_CODE_SUCCESS)) {
				
				String sign2 = (String) mapResult.get("sign");
				mapResult.remove("sign");
				SortedMap sm2 = new TreeMap(mapResult);
				
				// 验证是否是微信服务器的信息
				if (sign2.equals(CommonUtil.createSign(WeChatOaConfig.CHARSET, sm2,WeChatOaConfig.KEY))) {
					
					logger.info("订单查询（微信）成功！订单号为："+orderId+"的对应状态为："+mapResult.get("trade_state"));
					return new CommonResult(mapResult.get("trade_state"));
				
				}
				
				// 签名错误
				logger.error("订单查询（微信）失败！签名错误!订单号："+orderId);
				return new CommonResult(PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名错误");		
			} 
			
			// 业务码错误
			logger.error("订单查询失败！（微信）订单号："+orderId+"错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));			
			return new CommonResult(false, (String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("err_code_des")));
		} 
		
		// 通信码错误
		logger.error("订单查询失败！（微信）订单号："+orderId+"错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResult(false, (String)mapResult.get("return_code"), (String)mapResult.get("return_msg"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("return_msg")));

	}
	
	@Override
	public CommonResult orderQueryByOutTradeNo(String orderId) {
		return orderQuery(orderId, WeChatOaConfig.ORDER_QUERY_TYPE_OUT_TRADE_NO);
	}
	
	@Override
	public CommonResult orderQueryByTransactionId(String orderId) {
		return orderQuery(orderId, WeChatOaConfig.ORDER_QUERY_TYPE_TRANSACTION_ID);
	}
}
