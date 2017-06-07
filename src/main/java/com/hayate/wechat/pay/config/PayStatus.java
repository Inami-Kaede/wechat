package com.hayate.wechat.pay.config;

/**
 * @description 本地业务表交易状态为了保持统一，结果都使用微信支付的字段，支付宝交易状态用作支付宝的逻辑判断
 * @author kaede
 *
 */

public class PayStatus {
	
	
	//----------------------设备类型（开始）----------------------
	
	public static final int CLIENT_TYPE_ANDROID = 0;

	public static final int CLIENT_TYPE_IOS = 1;
	
	//----------------------设备类型（结束）----------------------
	
	//----------------------返利等级（开始）----------------------
	
	/**
	 * 一级
	 */
	public static final int REPAY_LEVEL_1 = 1;
	
	/**
	 * 二级
	 */
	public static final int REPAY_LEVEL_2 = 2;
	
	/**
	 * 教师
	 */
	public static final int REPAY_LEVEL_TEACHER = 0;
	
	//----------------------返利等级（结束）----------------------
	
	
	//----------------------抵扣券类型（开始）----------------------
	
	/**
	 * 通用抵扣券
	 */
	public static final int VOUCHERS_TYPE_ALL = -1;
	
	/**
	 * VIP抵扣券
	 */
	public static final int VOUCHERS_TYPE_VIP = 0;
	
	/**
	 * 课程抵扣券
	 */
	public static final int VOUCHERS_TYPE_COURSE = 1;
	
	/**
	 * 文章抵扣券
	 */
	public static final int VOUCHERS_TYPE_ARTICLE = 2;
	
	
	//----------------------抵扣券类型（结束）----------------------
	
	//----------------------支付类型名称（开始）----------------------
	
	/**
	 * 余额支付
	 */
	public static final int PAY_TYPE_REMAINING = -1;
	
	/**
	 * 微信支付
	 */
	public static final int PAY_TYPE_WECHAT = 0;
	
	/**
	 * 支付宝支付
	 */
	public static final int PAY_TYPE_ALIPAY = 1;
	
	//----------------------支付类型名称（开始）----------------------
	
	//----------------------产品类型（开始）----------------------
	/**
	 * VIP类型
	 */
	public static final int PROD_TYPE_VIP_TYPE = 0;
	
	/**
	 * 课程类型
	 */
	public static final int PROD_TYPE_COURSE = 1;
	
	/**
	 * 文章类型
	 */
	public static final int PROD_TYPE_ARTICLE = 2;
	
	//----------------------产品类型（结束）----------------------
	
	//----------------------产品类型名称（开始）----------------------
	/**
	 * VIP类型名称
	 */
	public static final String PROD_TYPE_VIP_TYPE_NAME = "会员";
	/**
	 * 课程类型名称
	 */
	public static final String PROD_TYPE_COURSE_NAME = "课程";
	/**
	 * 文章类型名称
	 */
	public static final String PROD_TYPE_ARTICLE_NAME = "文章";
	
	//----------------------产品类型名称（结束）----------------------
	
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
	
	
	//----------------------微信APP支付交易状态（开始）----------------------
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
	
	//----------------------微信APP支付交易状态（结束）----------------------
	
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
	
	//----------------------支付宝错误码（开始）----------------------
	
	/**
	 * 阿里系统错误
	 */
	public static final String ALI_BIZ_ERROR_SYSTEM_ERROR = "ACQ.SYSTEM_ERROR"; 
	
	public static final String ALI_BIZ_TRANSFER_ERROR_SYSTEM_ERROR = "SYSTEM_ERROR";
	
	//----------------------支付宝错误码（结束）----------------------
	
	//----------------------微信错误码（开始）----------------------
	
	/**
	 * 微信系统错误
	 */
	public static final String WECHAT_BIZ_ERROR_SYSTEM_ERROR = "SYSTEMERROR"; 
	
	//----------------------微信错误码（结束）----------------------
	
	
	
	//----------------------用户账户流水操作类型（开始）----------------------
	
	/**
	 * 用户账户操作类型：提现类型
	 */
	public static final int ACCOUNT_OPERATE_TYPE_WITHDRAW = 0;
	
	/**
	 * 用户账户操作类型：余额支付类型
	 */
	public static final int ACCOUNT_OPERATE_TYPE_REMAINING_PAY = 1;
	
	/**
	 * 用户账户操作类型：返利类型
	 */
	public static final int ACCOUNT_OPERATE_TYPE_REPAY = 2;
	
	//----------------------用户账户操作类型（结束）----------------------
	
	/**
	 * 企业付款描述信息
	 */
	public static final String WITHDRAW_DESC = "提现";
	
	/**
	 * 管理员邮箱
	 */
	public static final String ADMIN_EMAIL = "1541635693@qq.com";
	
	/**
	 * 默认优惠券类型（不能与已存在类型相同）
	 */
	public static final Integer DEFAULT_VOUCHER_TYPE = -100;
	
	/**
	 * 订单实际支付金额小于等于零的时候的返回DataEntity的code
	 */
	public static final Integer NOPAY_CODE = 1;
	
	/**
	 * 提现最小金额（元）
	 */
	public static final Integer MIN_AMOUNT = 1;
	
	/**
	 * 提现金额必须是该数据的倍数
	 */
	public static final Integer MULTIPLE = 2;
	
	/**
	 * 教师返利利率
	 */
	public static final float REPAY_TEACHER_RATE = 0.15F;
	
	/**
	 * 用户类型：普通用户
	 */
	public static final int REBRATE_TYPE_NORMAL = 0;
	
	/**
	 * 用户类型：特殊用户
	 */
	public static final int REBRATE_TYPE_SPECIAL = 1;
	
	/**
	 * 普通用户的type（返利时用）
	 */
	public static final int VIP_TYPE_NORMAL_USER = 0;
	
}
