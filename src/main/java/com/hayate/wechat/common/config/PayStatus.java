package com.hayate.wechat.common.config;

/**
 * @description 本地业务表交易状态为了保持统一，结果都使用微信支付的字段，支付宝交易状态用作支付宝的逻辑判断
 * @author kaede
 *
 */

public class PayStatus {
		
	
	
	//----------------------微信支付业务状态码（开始）----------------------
	/**
	 * 返回状态码	成功
	 */
	public final static String WECHAT_RETURN_CODE_SUCCESS = "SUCCESS";
	
	/**
	 * 返回状态码	失败
	 */
	public final static String WECHAT_RETURN_CODE_FAIL = "FAIL";
	
	/**
	 * 业务结果	成功
	 */
	public final static String WECHAT_RESULT_CODE_SUCCESS = "SUCCESS";
	
	/**
	 * 业务结果	失败
	 */
	public final static String WECHAT_RESULT_CODE_FAIL = "FAIL";
	
	/**
	 * 回调返回码	成功
	 */
	public final static String WECHAT_NOTIFY_RETURN_CODE_SUCCESS = "SUCCESS";
	
	/**
	 * 回调返回码	失败
	 */
	public final static String WECHAT_NOTIFY_RETURN_CODE_FAIL = "FAIL";
	//----------------------微信支付业务状态码（结束）----------------------
	
	//----------------------微信支付交易状态（开始）----------------------
	/**
	 * 支付成功 
	 */
	public static final String WECHAT_TRADE_STATUS_SUCCESS = "SUCCESS";         
	/**
	 * 转入退款 
	 */
	public static final String WECHAT_TRADE_STATUS_REFUND = "REFUND";           
	/**
	 * 未支付 
	 */
	public static final String WECHAT_TRADE_STATUS_NOTPAY = "NOTPAY";           
	/**
	 * 已关闭 
	 */
	public static final String WECHAT_TRADE_STATUS_CLOSED = "CLOSED";          
	/**
	 * 已撤销（刷卡支付）
	 */
	public static final String WECHAT_TRADE_STATUS_REVOKED = "REVOKED";         
	/**
	 * 用户支付中 
	 */
	public static final String WECHAT_TRADE_STATUS_USERPAYING = "USERPAYING";   
	/**
	 * 支付失败(其他原因，如银行返回失败)
	 */
	public static final String WECHAT_TRADE_STATUS_PAYERROR = "PAYERROR";       
	
	//----------------------微信支付交易状态（结束）----------------------
	
	//----------------------微信企业付款交易状态（开始）----------------------
	/**
	 * 转账成功 
	 */
	public static final String WECHAT_TRANSFER_TRADE_STATUS_SUCCESS = "SUCCESS"; 
	
	/**
	 * 转账失败 
	 */
	public static final String WECHAT_TRANSFER_TRADE_STATUS_FAILED = "FAILED"; 
	
	/**
	 * 处理中
	 */
	public static final String WECHAT_TRANSFER_TRADE_STATUS_PROCESSING = "PROCESSING"; 
	//----------------------微信企业付款交易状态（结束）----------------------
		
	//----------------------微信错误码（开始）----------------------
	
	/**
	 * 微信系统错误
	 */
	public static final String WECHAT_BIZ_ERROR_SYSTEM_ERROR = "SYSTEMERROR"; 
	
	//----------------------微信错误码（结束）----------------------	
	
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★华丽的分割线★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	
	//===========================================自定义错误码相关配置（开始）===================================================//
	
	/**
	 * 通信错误
	 */
	public final static String PAY_ERR_CODE_COMMUNICATION_ERROR = "COMMUNICATION_ERROR";
	
	/**
	 * 未知错误
	 */
	public final static String PAY_ERR_CODE_UNKNOWN_ERROR = "UNKNOWN_ERROR";
	
	/**
	 * 签名错误
	 */
	public final static String PAY_ERR_CODE_SIGNATURE_ERROR = "SIGNATURE_ERROR";
	
	/**
	 * 金额不一致
	 */
	public final static String PAY_ERR_CODE_AMOUNT_ERROR = "AMOUNT_ERROR";
	
	
	/**
	 * 重复请求
	 */
	public final static String PAY_ERR_CODE_REPETITIVE_SUBMISSION = "REPETITIVE_SUBMISSION";
	
	
	/**
	 * 订单状态错误
	 */
	public final static String PAY_ERR_CODE_TRADE_STATUS_ERROR = "TRADE_STATUS_ERROR";
	
	
	/**
	 * 因为支付宝的订单被关闭也会有回调信息，所以此处单独列出来一个状态用作业务处理（也可以不在回调中处理，通过每天定时任务去处理被关闭的订单）
	 * 订单被关闭（用于支付宝回调业务处理）
	 */
	public final static String PAY_ERR_CODE_ALI_ORDER_CLOSED = "ALI_ORDER_CLOSED";
	
	//===========================================自定义错误码相关配置（结束）===================================================//
	
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★华丽的分割线★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	
	//----------------------支付宝支付业务状态码（结束）----------------------
	/**
	 * 支付宝回调返回码	成功
	 */
	public final static String ALI_NOTIFY_RETURN_CODE_SUCCESS = "success";
	
	/**
	 * 支付宝回调返回码	成功
	 */
	public final static String ALI_NOTIFY_RETURN_CODE_FAILURE = "failure";
	//----------------------支付宝支付业务状态码（结束）----------------------
	
	
	//----------------------支付宝交易状态（开始）----------------------
	/**
	 * 交易结束，不可退款
	 */
	public static final String ALI_TRADE_STATUS_TRADE_FINISHED = "TRADE_FINISHED";	
	/**
	 * 交易支付成功
	 */
	public static final String ALI_TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";	
	/**
	 * 交易创建，等待买家付款
	 */
	public static final String ALI_TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";		
	/**
	 * 未付款交易超时关闭，或支付完成后全额退款
	 */
	public static final String ALI_TRADE_STATUS_TRADE_CLOSED = "TRADE_CLOSED";

	//----------------------支付宝交易状态（结束）----------------------
	
	
	//----------------------支付宝付款交易状态（开始）----------------------
	/**
	 * 成功（配合"单笔转账到银行账户接口"产品使用时, 同一笔单据多次查询有可能从成功变成退票状态）
	 */
	public static final String ALI_TRANSFER_TRADE_STATUS_SUCCESS = "SUCCESS";
	/**
	 * 失败（具体失败原因请参见error_code以及fail_reason返回值）
	 */
	public static final String ALI_TRANSFER_TRADE_STATUS_FAIL = "FAIL";
	/**
	 * 等待处理
	 */
	public static final String ALI_TRANSFER_TRADE_STATUS_INIT = "INIT";
	/**
	 * 处理中
	 */
	public static final String ALI_TRANSFER_TRADE_STATUS_DEALING = "DEALING";
	/**
	 * 退票（仅配合"单笔转账到银行账户接口"产品使用时会涉及, 具体退票原因请参见fail_reason返回值）
	 */
	public static final String ALI_TRANSFER_TRADE_STATUS_REFUND = "REFUND";
	/**
	 * 状态未知。 
	 */
	public static final String ALI_TRANSFER_TRADE_STATUS_UNKNOWN = "UNKNOWN";
	//----------------------支付宝付款交易状态（结束）----------------------
	
	//----------------------支付宝错误码（开始）----------------------
	
	/**
	 * 阿里系统错误
	 */
	public static final String ALI_BIZ_ERROR_SYSTEM_ERROR = "ACQ.SYSTEM_ERROR"; 
	
	public static final String ALI_BIZ_TRANSFER_ERROR_SYSTEM_ERROR = "SYSTEM_ERROR";
	
	//----------------------支付宝错误码（结束）----------------------
	
}
