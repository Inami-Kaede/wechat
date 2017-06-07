package com.hayate.wechat.pay.config;

public class AliPayConfig {

	//-----------正式环境（开始）---------------------
	
	/**
	 * 正式环境网关
	 */
/*	public static final String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
	
	public static final String APP_ID = "";

	public static final String PRIVATE_KEY = "";

	public static final String PUBLIC_KEY = "";

	public static final String SIGN_TYPE = "RSA2";*/
	
	//-----------正式环境（结束）---------------------
	
	//-----------沙箱环境（开始）---------------------
	
	/**
	 * 沙箱环境网关
	 */
	public static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
	
	public static final String APP_ID = "2016072900118609";

	public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5igy9bbloVikMw/p3nmcNOF+EkOkRNarovUJCQw+5WvYMJU32Kf3kGAES39zh1ostVT312L+u4rAintFD+3plXkdfCU+jneniYv4KRrwC7RRmXWjHLDiz2Vnm0tw65IjVCHALS7xs47SMAahZlqWT1igss+h4UUxK9kIVkDqH5AgMBAAECgYEAhl2PmTDI8Y6BT050EbU/bBZDHg3s+23vW3BTkmCJJ58cPNg1xZiuqdCu6kQZsB4sBqJAMnqbZ/SOUcjuji4j0xvCVm+FxC505s2kecW+raeVwhrGcsHvbl5+kzWGIM+haltOxmwhzbo4PfU8PdZ7bBk0udPWedNFrLNJ986T32UCQQDZcP95lBt/4xYiKuY0TtyWXBbPTVTZltebmCeYiCEDVVyJp+sUqXxPL4+AoqhwieKDB9FcCSDZ9d6HQacue4F7AkEAzU7pUZrUyjmoCSbPpdVK8MhLDl+AAqLNw1rm9ApazeUW6SqrxW3ZS2bQYflJ60gaoj1tD95uWPEA4e5jR1XOGwJBAKOPRQyd5OWY2qBO5qS4IWOU16+NuuKFfZ9Tn40z8KlCP35oagM7zf7+hpI7SDqG7XSkn838K9LoCEBzmR701hsCQHpfebSZXBI5cY4a4xQOYT/JsusbQzrO/WRez2BQt7UXwqMyOvZPtoA6BSUQqN2jubRQdqxJwW4kIImKRQSlAVcCQCN4xCO8qZJo2ByV36gImAkiR0zs1Z43H+24mHZe3haaowC9b4VlEUSGDFY5fzGECQrcQR7zWBc2AGZ0g9rgPVY=";

	public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

	public static final String SIGN_TYPE = "RSA";
	
	//-----------沙箱环境（结束）---------------------

	public static final String CHARSET = "utf-8";
	
	public static final String FORMAT = "json";

	

	
	
	/**
	 * ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成
	 */
	public static final String PAYEE_TYPE_USERID  = "ALIPAY_USERID";
	
	/**
	 * ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。
	 */
	public static final String PAYEE_TYPE_LOGONID  = "ALIPAY_LOGONID";
	
	/**
	 * 支付宝统一接口的回调action
	 */
	public final static String NOTIFY_URL = "http://1v6721s159.iask.in/yd-service/ms/v1/pay/alipayback";
	//public final static String NOTIFY_URL = "http://m.youda999.com/ms/v1/pay/alipayback";

}
