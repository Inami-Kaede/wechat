package com.hayate.wechat.pay.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.PayStatus;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.pojo.CommonResponse;
import com.hayate.wechat.common.util.Arith;
import com.hayate.wechat.common.util.CommonUtil;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.common.util.MailUtil;
import com.hayate.wechat.common.util.NetUtil;
import com.hayate.wechat.pay.service.WeChatPayService;


@Service
public class WeChatPayServiceImpl extends BaseService implements WeChatPayService{

	/**
	 * 微信统一下单
	 * @param orderId	订单号
	 * @param totalPrice	金额
	 * @param date	下单时间
	 * @param tradeType	下单类型（0：JSAPI 1：NATIVE 2：APP）
	 * @param openId	用户openid（只有下单类型为 0：JSAPI时有用）
	 * @return
	 */
	@Override
	public CommonResponse unifiedOrder(String orderId,Double totalPrice,Date date,int tradeType,String openId) {
		
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
			return new CommonResponse(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR,"请求未响应或为空");
		
		}
		
		//包装返回到手机的数据
		Map<String, Object> mapResult = CommonUtil.parseXml(xmlResult);
		
		logger.debug("---------返回的数据（开始）-------------");
		
		for(Entry<String, Object> e : mapResult.entrySet()){
			
			logger.debug(e.getKey()+":"+e.getValue());
		
		}
		
		logger.debug("---------返回的数据（结束）-------------");
		
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
						return new CommonResponse(codeUrl);
						
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
					
					return new CommonResponse(sm3);		
					
				}
				
				// 签名错误
				logger.error("统一下单：签名错误！（微信）对应订单号："+orderId);
				return new CommonResponse(PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名错误");	
			
			} 
			
			// 业务码错误
			logger.error("统一下单：业务码错误（微信）！对应订单号："+orderId+"错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));			
			return new CommonResponse((String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"));
		}
		// 通信码错误
		logger.error("统一下单：通信错误（微信）！错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResponse((String)mapResult.get("return_code"), (String)mapResult.get("return_msg"));
	}
	
	/**
	 * JSAPI下单
	 * @param orderId
	 * @param totalPrice
	 * @param date
	 * @param openId
	 * @return
	 */
	@Override
	public CommonResponse unifiedOrderJsapi(String orderId,Double totalPrice,Date date,String openId) {
		return unifiedOrder(orderId, totalPrice, date, WeChatOaConfig.INT_TRADE_TYPE_JSAPI, openId);
	}
	
	/**
	 * 扫码下单
	 * @param orderId
	 * @param totalPrice
	 * @param date
	 * @return
	 */
	@Override
	public CommonResponse unifiedOrderNative(String orderId,Double totalPrice,Date date) {
		return unifiedOrder(orderId, totalPrice, date, WeChatOaConfig.INT_TRADE_TYPE_NATIVE, null);
	}
	
	/**
	 * APP下单
	 * @param orderId
	 * @param totalPrice
	 * @param date
	 * @return
	 */
	@Override
	public CommonResponse unifiedOrderApp(String orderId,Double totalPrice,Date date) {
		return unifiedOrder(orderId, totalPrice, date, WeChatOaConfig.INT_TRADE_TYPE_APP, null);
	}
	
	
	/**
	 * 微信支付回调
	 * @param request
	 * @return
	 */
	@Override
	public CommonResponse notify(HttpServletRequest request) {
		
		Map<String, Object> mapResult = CommonUtil.parseXml(request);
		
		logger.debug("---------返回的数据（开始）-------------");
		
		for(Entry<String, Object> e : mapResult.entrySet()){
			
			logger.debug(e.getKey()+":"+e.getValue());
		
		}
		
		logger.debug("---------返回的数据（结束）-------------");
			
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
					@SuppressWarnings("unused")
					String transactionId = (String)mapResult.get("transaction_id");
					
					
					//TODO 根据订单号查询本地数据库					
					Double payAmount = 0.01; //本地订单金额
					Integer userId = 1;		//下单用户ID
					String orderStatus = PayStatus.WECHAT_TRADE_STATUS_NOTPAY;	//本地数据库订单状态
					
					
					
					//判断金额是否一致
					if (!String.valueOf(Arith.mul(payAmount, 100)).equals(totalFee)){
						
						logger.error("本地金额与订单金额信息不匹配！（微信）本地金额："+payAmount+"订单金额："+Double.parseDouble(totalFee)/100);
						return new CommonResponse(false, PayStatus.PAY_ERR_CODE_AMOUNT_ERROR, "金额不一致", CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, "金额不一致"));
					}
					
					//判断是否已处理
					if (!orderStatus.equals(PayStatus.WECHAT_TRADE_STATUS_NOTPAY)){
						
						logger.info("（微信）支付结果通知：订单号为："+orderId+"的订单已处理,该信息为重复的通知");
						return new CommonResponse(false, PayStatus.PAY_ERR_CODE_REPETITIVE_SUBMISSION, "重复的通知",CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_SUCCESS, "成功"));
					} 												
					
					
					logger.info("订单（微信）已成功支付！订单号为："+orderId+"对应用户ID为："+userId);					
					
					
					
					//TODO 返回成功订单的ID 用作后续订单处理
					
					
					
					return new CommonResponse(orderId);
				}
				
				// 签名错误
				logger.error("（微信）支付结果通知：签名错误！");
				return new CommonResponse(false, PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名错误", CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, "签名错误"));
			} 
			
			// 业务码错误
			logger.error("（微信）支付结果通知：业务码错误！错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));
			return new CommonResponse(false, (String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("err_code_des")));
					
		} 
		
		// 通信码错误
		logger.error("（微信）支付结果通知：通信错误！错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResponse(false, (String)mapResult.get("return_code"), (String)mapResult.get("return_msg"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("return_msg")));
	}
	
	/**
	 * 订单查询（查询统一下单接口的订单）
	 * @param orderId
	 * @param orderIdType 订单号类型（0：微信支付订单号 1：商户订单号）
	 * @return
	 */
	@Override
	public CommonResponse orderQuery(String orderId,int orderIdType) {
				
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
			return new CommonResponse(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR,"请求未响应或为空");
		
		}
		
		Map<String, Object> mapResult = CommonUtil.parseXml(xmlResult);
		
		logger.debug("---------返回的数据（开始）-------------");
		
		for(Entry<String, Object> e : mapResult.entrySet()){
			
			logger.debug(e.getKey()+":"+e.getValue());
		
		}
		
		logger.debug("---------返回的数据（结束）-------------");
		
		// 判断通信是否成功
		if (mapResult.get("return_code").equals(PayStatus.WECHAT_RETURN_CODE_SUCCESS)) {
			
			// 判断业务是否成功
			if (mapResult.get("result_code").equals(PayStatus.WECHAT_RESULT_CODE_SUCCESS)) {
				
				String sign2 = (String) mapResult.get("sign");
				mapResult.remove("sign");
				SortedMap<Object, Object> sm2 = new TreeMap<Object, Object>(mapResult);
				
				// 验证是否是微信服务器的信息
				if (sign2.equals(CommonUtil.createSign(WeChatOaConfig.CHARSET, sm2,WeChatOaConfig.KEY))) {
					
					logger.info("订单查询（微信）成功！订单号为："+orderId+"的对应状态为："+mapResult.get("trade_state"));
					return new CommonResponse(mapResult.get("trade_state"));
				
				}
				
				// 签名错误
				logger.error("订单查询（微信）失败！签名错误!订单号："+orderId);
				return new CommonResponse(PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名错误");		
			} 
			
			// 业务码错误
			logger.error("订单查询失败！（微信）订单号："+orderId+"错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));			
			return new CommonResponse(false, (String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("err_code_des")));
		} 
		
		// 通信码错误
		logger.error("订单查询失败！（微信）订单号："+orderId+"错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResponse(false, (String)mapResult.get("return_code"), (String)mapResult.get("return_msg"), CommonUtil.setXML(PayStatus.WECHAT_NOTIFY_RETURN_CODE_FAIL, (String)mapResult.get("return_msg")));

	}
	
	/**
	 * 订单查询（商户订单号）
	 * @param orderId
	 * @return
	 */
	@Override
	public CommonResponse orderQueryByOutTradeNo(String orderId) {
		return orderQuery(orderId, WeChatOaConfig.ORDER_QUERY_TYPE_OUT_TRADE_NO);
	}
	
	/**
	 * 订单查询（微信订单号）
	 * @param orderId
	 * @return
	 */
	@Override
	public CommonResponse orderQueryByTransactionId(String orderId) {
		return orderQuery(orderId, WeChatOaConfig.ORDER_QUERY_TYPE_TRANSACTION_ID);
	}
	
	/**
	 * 关闭订单（关闭统一下单接口的订单）
	 * 订单号仅支持商户本地订单号
	 */
	@Override
	public CommonResponse closeOrder(String orderId) {
		
		
		logger.info("关闭微信订单，订单号为:"+orderId);
		//准备数据
		SortedMap<Object, Object> sm = new TreeMap<Object, Object>();
		sm.put("appid", WeChatOaConfig.APP_ID);
		sm.put("mch_id", WeChatOaConfig.MCHID);
		sm.put("out_trade_no", orderId);
		sm.put("nonce_str", CommonUtil.CreateNoncestr());
		
		String sign = CommonUtil.createSign(WeChatOaConfig.CHARSET, sm,WeChatOaConfig.KEY);
		sm.put("sign", sign);
		
		String requestXml = CommonUtil.getRequestXml(sm);
		logger.debug("订单关闭（第三方----微信）-发送数据："+requestXml);
		
		//发送数据
		String xmlResult = HttpClientUtil.doPostXml(WeChatOaConfig.PAY_CLOSE_ORDER, requestXml);
		
		if(StringUtils.isEmpty(xmlResult)){
			
			logger.error("关闭微信订单失败！返回值为空!对应单号为："+orderId);
			return new CommonResponse(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR, "返回值为空");
		
		}
		
		Map<String, Object> mapResult = CommonUtil.parseXml(xmlResult);
		
		logger.debug("---------返回的数据（开始）-------------");
		
		for(Entry<String, Object> e : mapResult.entrySet()){
			
			logger.debug(e.getKey()+":"+e.getValue());
		
		}
		
		logger.debug("---------返回的数据（结束）-------------");
		
		// 判断通信是否成功
		if (mapResult.get("return_code").equals(PayStatus.WECHAT_RETURN_CODE_SUCCESS)) {			
			
			String sign2 = (String) mapResult.get("sign");
			mapResult.remove("sign");
			SortedMap<Object, Object> sm2 = new TreeMap<Object, Object>(mapResult);
			
			// 验证是否是微信服务器的信息
			if (sign2.equals(CommonUtil.createSign(WeChatOaConfig.CHARSET, sm2,WeChatOaConfig.KEY))) {
				
				String errCode = (String) mapResult.get("err_code");
				
				if(!StringUtils.isEmpty(errCode)){
					
					logger.error("关闭微信订单失败！订单号："+orderId+"错误信息："+errCode+":"+mapResult.get("err_code_des"));	
					return new CommonResponse(errCode, (String)mapResult.get("err_code_des"));
				}			
				logger.info("关闭微信订单成功！对应订单号："+orderId);
				return new CommonResponse(true);			
			} 
			
			logger.error("关闭订单失败（第三方----微信）！签名错误!订单号："+orderId);
			return new CommonResponse(PayStatus.PAY_ERR_CODE_SIGNATURE_ERROR, "签名错误");
			
		}
		
		// 通信码错误
		logger.error("关闭订单失败！（第三方----微信）订单号："+orderId+"错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResponse((String)mapResult.get("return_code"), (String)mapResult.get("return_msg"));
	}
	
	@Override
	public CommonResponse transfers(Double amount,String orderId,String openId) {
		
		//准备发送请求
		SortedMap<Object, Object> sm = new TreeMap<Object, Object>();
		sm.put("mch_appid", WeChatOaConfig.APP_ID);
		sm.put("mchid", WeChatOaConfig.MCHID);
		sm.put("nonce_str", CommonUtil.CreateNoncestr());
		sm.put("partner_trade_no", orderId);
		sm.put("openid", openId);
		sm.put("check_name", "NO_CHECK");
		sm.put("amount", String.valueOf((int)(Arith.mul(amount, 100))));
		sm.put("desc", WeChatOaConfig.TRANSFERS_DESC);
		sm.put("spbill_create_ip", NetUtil.getLocalIp());
		
		String sign = CommonUtil.createSign(WeChatOaConfig.CHARSET, sm,WeChatOaConfig.KEY);
		sm.put("sign", sign);
		
		String requestXml = CommonUtil.getRequestXml(sm);
			
		String xmlResult = HttpClientUtil.doCertPostXml(WeChatOaConfig.PAY_TRANSFERS, requestXml, WeChatOaConfig.CERT_PATH, WeChatOaConfig.CERT_PASSWORD);
		
		logger.debug("提现（微信）返回数据："+xmlResult);
		
		if(StringUtils.isEmpty(xmlResult)){
			
			logger.error("错误：请求响应为空！（微信）");
			return new CommonResponse(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR, "请求响应为空");
		
		}
		
		Map<String, Object> mapResult = CommonUtil.parseXml(xmlResult);
		
		logger.debug("---------返回的数据（开始）-------------");
		
		for(Entry<String, Object> e : mapResult.entrySet()){
			
			logger.debug(e.getKey()+":"+e.getValue());
		
		}
		
		logger.debug("---------返回的数据（结束）-------------");
				
		// 判断通信是否成功
		if (mapResult.get("return_code").equals(PayStatus.WECHAT_RETURN_CODE_SUCCESS)) {
			
			// 判断业务是否成功
			if (mapResult.get("result_code").equals(PayStatus.WECHAT_RESULT_CODE_SUCCESS)) {
				
				//判断支付返回结果
				if(!StringUtils.isEmpty(mapResult.get("partner_trade_no")) && !StringUtils.isEmpty(mapResult.get("payment_no"))
						&& !StringUtils.isEmpty(mapResult.get("payment_time"))){
										
					logger.info("用户完成提现（微信），金额为："+amount+"对应订单号："+orderId);
					return new CommonResponse(mapResult.get("payment_time"));
				
				}
				
				logger.error("错误：提现结果返回不完整！请立即进行检查排错！（微信）");
				logger.error("对应订单号："+orderId);
				logger.error("金额："+amount);
				logger.error("返回的订单号为："+mapResult.get("partner_trade_no"));
				logger.error("返回的微信订单号为："+mapResult.get("payment_no"));
				logger.error("返回的微信支付成功时间为："+mapResult.get("payment_time"));

				//通知管理员					
				String title = "微信企业打款出现错误！！";
				String body = "错误：微信企业打款结果返回不完整！（微信）请立即进行检查排错！对应订单号："+orderId+"金额："+amount+
							"返回的订单号为："+mapResult.get("partner_trade_no")+"返回的微信订单号为："+mapResult.get("payment_no")+"返回的微信支付成功时间为："+mapResult.get("payment_time");
					
				MailUtil.sendMail(WeChatOaConfig.ADMIN_MAIL_ADRESS,title,body);

				return new CommonResponse(PayStatus.PAY_ERR_CODE_UNKNOWN_ERROR, "结果返回不完整");
			}	
			
			//如果余额不足
			if(mapResult.get("err_code").equals("NOTENOUGH")){
				
				//通知管理员				
				MailUtil.sendMail(WeChatOaConfig.ADMIN_MAIL_ADRESS, "企业打款（微信）账户余额不足！请及时充值！","请登录商户号后台查看余额！");

			}
			
			// 业务码错误
			logger.error("打款（微信）错误!对应订单号："+orderId+"错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));
			return new CommonResponse((String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"));
		}
		
		// 通信码错误
		logger.error("打款（微信）错误！对应订单号："+orderId+"错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResponse((String)mapResult.get("return_code"), (String)mapResult.get("return_msg"));
	}
	
	@Override
	public CommonResponse transfersQuery(String orderId) {
	
		//准备数据
		SortedMap<Object, Object> sm = new TreeMap<Object, Object>();
		sm.put("appid", WeChatOaConfig.APP_ID);
		sm.put("mch_id", WeChatOaConfig.MCHID);
		sm.put("partner_trade_no", orderId);
		sm.put("nonce_str", CommonUtil.CreateNoncestr());
		String sign = CommonUtil.createSign(WeChatOaConfig.CHARSET, sm,WeChatOaConfig.KEY);
		sm.put("sign", sign);		
		
		String requestXml = CommonUtil.getRequestXml(sm);
		
		//发送数据
		String doCertPostXml = HttpClientUtil.doCertPostXml(WeChatOaConfig.PAY_GET_TRANSFER_INFO, requestXml, WeChatOaConfig.CERT_PATH, WeChatOaConfig.CERT_PASSWORD);		
		
		if(StringUtils.isEmpty(doCertPostXml)){
			
			logger.error("错误：请求响应为空（微信）");
			return new CommonResponse(PayStatus.PAY_ERR_CODE_COMMUNICATION_ERROR, "请求响应为空");
		
		}
		
		Map<String, Object> mapResult = CommonUtil.parseXml(doCertPostXml);
		
		logger.debug("---------返回的数据（开始）-------------");
		
		for(Entry<String, Object> e : mapResult.entrySet()){
			
			logger.debug(e.getKey()+":"+e.getValue());
		
		}
		
		logger.debug("---------返回的数据（结束）-------------");
		
		//判断通信是否成功
		if(mapResult.get("return_code").equals(PayStatus.WECHAT_RETURN_CODE_SUCCESS)){
			
			// 判断业务是否成功
			if (mapResult.get("result_code").equals(PayStatus.WECHAT_RESULT_CODE_SUCCESS)) {
				
				logger.info("提现查询（微信）成功！订单号为："+orderId+"的对应状态为："+mapResult.get("status"));
				return new CommonResponse(mapResult.get("status"));
			
			}
			
			logger.error("提现查询失败！（微信）订单号："+orderId+"错误信息："+mapResult.get("err_code")+":"+mapResult.get("err_code_des"));
			return new CommonResponse((String)mapResult.get("err_code"), (String)mapResult.get("err_code_des"));
		}
		
		logger.error("提现查询失败！（微信）订单号："+orderId+"错误信息："+mapResult.get("return_code")+":"+mapResult.get("return_msg"));
		return new CommonResponse((String)mapResult.get("return_code"), (String)mapResult.get("return_msg"));
	}
}
