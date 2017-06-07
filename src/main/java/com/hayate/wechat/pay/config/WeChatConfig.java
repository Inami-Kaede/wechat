package com.hayate.wechat.pay.config;

import java.io.File;

public class WeChatConfig {
	
	// APP支付//
	public final static String APP_ID = "wxc7f2eca4f19be57d";
	
	public final static String APP_SECRET = "dfc316f567dfe75c8eadd002526666fb";//

	// ---------------------------------------支付相关---------------------------------------//
	// 受理商ID，身份标识
	public final static String MCHID = "1414334102";
	
	// 商户支付密钥Key。审核通过后，在微信发送的邮件中查看
	public final static String KEY = "3mHxQ85ba2I8EDEqVooVuvokfjg8P5nY";
	
	public final static String SIGN_TYPE = "MD5";// 签名加密方式

	public final static String CERT_PATH = new File(WeChatConfig.class.getResource("/").getPath() +"apiclient_cert.p12").getPath();

	public final static String CERT_PASSWORD = MCHID;
	
	public final static String CHARSET = "UTF-8";

	// --------------------------------------------------------------------//
	/**
	 * 微信支付接口地址
	 */
	// 微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	// 对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	// 短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	// 接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
	
	// 获取openid和access_token(第三方登陆)
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	// 企业打款
	public final static String TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	// 企业打款查询
	public final static String CHEACK_TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
	
	// 微信支付统一接口的回调action
	public final static String NOTIFY_URL = "http://1v6721s159.iask.in/yd-service/ms/v1/pay/wechatback";
	//public final static String NOTIFY_URL = "http://m.youda999.com/ms/v1/pay/wechatback";
	
	// 微信支付成功支付后跳转的地址
	//public final static String SUCCESS_URL = "http://1v6721s159.iask.in/jeesite/f/wechat/success";	
	
} 
