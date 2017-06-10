package com.hayate.wechat.common.config;

import java.io.File;

public class WeChatOaConfig {
	
	//===========================================公众号配置（开始）===================================================//	
	
	//--------------------------生产环境（开始）------------------------------------------//
	
/*	public final static String APP_ID = "";
	
	public final static String APP_SECRET = "";*/
	
	/**
	 * token
	 */
	public final static String TOKEN = "233233";

	/**
	 * encodingAESKey
	 */
	public final static String ENCODING_AES_KEY = "AHPHihfW9WhLWgSZ2OfGklsP6K7HYzeghaFOu7KCugh";

	/**
	 * 是否开启消息加密
	 */
	public final static boolean OPEN_ENCODING_AES_KEY = false;
	//--------------------------生产环境（结束）------------------------------------------//
		
	//--------------------------测试环境（开始）------------------------------------------//
	//测试号
	public final static String APP_ID = "wx8b296abd43cffd5d";
	
	public final static String APP_SECRET = "edcec25566310e3c96ada82b331255e7";
	//--------------------------测试环境（结束）------------------------------------------//
	
	//===========================================公众号配置（结束）===================================================//
			

	
	
		
	//===========================================支付相关配置（开始）===================================================//
		
	//--------------------------基本配置（开始）------------------------------------------//
	/**
	 * 受理商ID，身份标识
	 */
	public final static String MCHID = "1388316602";
	
	/**
	 * 商户支付密钥Key。审核通过后，在微信发送的邮件中查看
	 */
	public final static String KEY = "LHo0mjdEbsAv8tW6VZxnmk0xmAoadio2";
	
	/**
	 * 签名加密方式
	 */
	public final static String SIGN_TYPE = "MD5";
	
	public final static String CHARSET = "UTF-8";
	
	/**
	 * 证书路径
	 */
	public final static String CERT_PATH = new File(WeChatOaConfig.class.getResource("/").getPath() +"apiclient_cert.p12").getPath();

	/**
	 * 证书密码
	 */
	public final static String CERT_PASSWORD = MCHID;
	
	
	/**
	 * 企业付款描述信息
	 */
	public final static String TRANSFERS_DESC = "提现";
	
	/**
	 * 被通知的邮箱地址
	 */
	public final static String ADMIN_MAIL_ADRESS = "tomkarry@foxmail.com";
	
	/**
	 * 订单过期时间（分钟）
	 */
	public final static int ORDER_EXPIRE = 90;
	
	public final static String TRADE_TYPE_JSAPI = "JSAPI";
	
	public final static String TRADE_TYPE_NATIVE = "NATIVE";
	
	public final static String TRADE_TYPE_APP = "APP";
	
	public final static int INT_TRADE_TYPE_JSAPI = 0;
	
	public final static int INT_TRADE_TYPE_NATIVE = 1;
	
	public final static int INT_TRADE_TYPE_APP = 2;
	
	/**
	 * 订单查询类型：微信支付订单号
	 */
	public final static int ORDER_QUERY_TYPE_TRANSACTION_ID = 0;
	
	/**
	 * 订单查询类型：商户订单号
	 */
	public final static int ORDER_QUERY_TYPE_OUT_TRADE_NO = 1;
	
	//--------------------------基本配置（结束）------------------------------------------//
	
	//--------------------------支付相关url地址（开始）------------------------------------------//
	/**
	 * 微信支付接口地址	POST
	 */
	public final static String PAY_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 微信退款接口	POST
	 */
	public final static String PAY_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/**
	 * 订单查询接口(POST)
	 */
	public final static String PAY_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
	 
	/**
	 * 关闭订单接口(POST)
	 */
	public final static String PAY_CLOSE_ORDER = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	/**
	 * 退款查询接口(POST)
	 */
	public final static String PAY_REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	/**
	 * 对账单接口(POST)
	 */
	public final static String PAY_DOWNLOAD_BILL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
	/**
	 * 接口调用上报接口(POST)
	 */
	public final static String PAY_REPORT = "https://api.mch.weixin.qq.com/payitil/report";
	 
	/**
	 * 企业打款	POST
	 */
	public final static String PAY_TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	/**
	 * 企业打款查询　POST
	 */
	public final static String PAY_GET_TRANSFER_INFO = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";
	
	/**！！！！！！！！！！！！！！！！！！！！！！！非常重要！！！！！！！！！！！！！！！！！！！！！！！！！！！
	 * 微信支付统一接口的回调action
	 */
	public final static String NOTIFY_URL = "http://1v6721s159.iask.in/yd-service/ms/v1/pay/wechatback";
			 
	/**
	 * 微信支付成功支付后跳转的地址	
	 */
	public final static String SUCCESS_URL = "http://www.baidu.com";
	//--------------------------支付相关url地址（结束）------------------------------------------//
	
	//===========================================支付相关配置（结束）===================================================//
		
	
	
	//==========================公众号接入相关（开始）============================================
	/**
	 * 公众号accessToken redis的KEY
	 */
	public final static String REDIS_KEY_ACCESS_TOKEN = "WeChat:OA:AccessToken";
	
	/**
	 * 获取access_token(微信公众号)	GET
	 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	 */
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
		

		
	//==========================公众号接入相关（结束）============================================
		
	
	
	
	//==========================公众号事件key（开始）============================================
	/**
	 * 点击事件：获取二维码
	 */
	public static final String EVENT_KEY_QRCODE = "qrcode";
	
	//==========================公众号事件key（结束）============================================
	
	
	
	
	
	
	//===========================================自定义菜单模块（开始）===================================================//	
	
	//--------------------------自定义菜单创建接口（开始）------------------------------------------//
	/**
	 * 创建自定义菜单菜单	POST
	 * https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
	 */
	public final static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create";
	//--------------------------自定义菜单创建接口（结束）------------------------------------------//
	
	//--------------------------自定义菜单查询接口（开始）------------------------------------------//
	/**
	 * 创建自定义菜单菜单	GET
	 * https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN
	 */
	public final static String MENU_GET = "https://api.weixin.qq.com/cgi-bin/menu/get";
	//--------------------------自定义菜单查询接口（结束）------------------------------------------//
	
	//--------------------------自定义菜单删除接口（开始）------------------------------------------//
	/**
	 * 删除当前使用的自定义菜单	GET
	 * https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN
	 */
	public final static String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete";
	//--------------------------自定义菜单删除接口（结束）------------------------------------------//
	
	//===========================================自定义菜单模块（结束）===================================================//	
	
	
	
	
	
	//===========================================微信网页开发模块（开始）===================================================//	
	
	public final static String REDIRECT_URI = "http://1v6721s159.iask.in/yd-service/ms/v1/oa/redirect";
	
	public final static String LOGIN_SCOPE_BASE = "snsapi_base";
	
	public final static String LOGIN_SCOPE_USERINFO = "snsapi_userinfo";
	
	//--------------------------微信网页授权（开始）------------------------------------------//
	/**
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 * 用户授权（获取code，用于登录）
	 * appid	公众号的唯一标识
	 * redirect_uri	授权后重定向的回调链接地址，请使用urlEncode对链接进行处理
	 * response_type	返回类型，请填写code
	 * scope	应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
	 * 			snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * state（非必须）	重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * #wechat_redirect	无论直接打开还是做页面302重定向时候，必须带此参数
	 * 
	 */
	public final static String OAUTH2_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	/**
	 * 通过code换取用户级别的access_token	GET
	 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code 
	 */
	public final static String OAUTH2_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
		
	/**
	 * 刷新access_token（如果需要）	GET
	 * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN 
	 */
	public final static String OAUTH2_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
		
	/**
	 * 拉取用户信息	GET
	 * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN 
	 */
	public final static String SNS_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";
		
	/**
	 * 检验授权凭证（access_token）是否有效	GET
	 * https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID 
	 */
	public final static String SNS_AUTH = "https://api.weixin.qq.com/sns/auth";
	//--------------------------微信网页授权（结束）------------------------------------------//
	
	//===========================================微信网页开发模块（结束）===================================================//	
	
	
	
	
	
	
	//===========================================账号管理模块（开始）===================================================//	
	/**
	 * 生成的二维码宽
	 */
	public final static int QRCODE_WIDTH = 200;
	/**
	 * 生成的二维码高
	 */
	public final static int QRCODE_HEIGHT = 200;
	
	/**
	 * 临时带参二维码过期时间（最大值：2592000）
	 */
	public final static int QRCODE_EXPIRE = 2592000;
	
	//--------------------------生成带参数的二维码（开始）------------------------------------------//
	/**
	 * 创建二维码ticket(会返回ticket和url)	POST
	 * https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN
	 */
	public final static String QRCODE_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	
	/**
	 * 通过ticket换取二维码	本接口无须登录态即可调用。
	 * https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET
	 */
	public final static String SHOW_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
	//--------------------------生成带参数的二维码（结束）------------------------------------------//
	
	//--------------------------长链接转短链接接口（开始）------------------------------------------//
	/**
	 * 长链接转短链接接口	POST
	 * https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN
	 */
	public final static String SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl";
	//--------------------------长链接转短链接接口（结束）------------------------------------------//
	
	//===========================================账号管理模块（结束）===================================================//
	
	
	
	
	
	//===========================================消息管理模块（开始）===================================================//
	
	//--------------------------发送消息-群发接口和原创校验（开始）------------------------------------------//
	public static final String PUSH_TYPE_MPNEWS = "mpnews";
	
	public static final String PUSH_TYPE_TEXT = "text";
	
	public static final String PUSH_TYPE_VOICE = "voice";
	
	public static final String PUSH_TYPE_IMAGE = "image";
	
	public static final String PUSH_TYPE_MPVIDEO = "mpvideo";
	
	public static final String PUSH_TYPE_WXCARD = "wxcard";
	
	
	public static final int INT_PUSH_TYPE_IMAGE = 0;
	
	public static final int INT_PUSH_TYPE_VOICE = 1;
	
	public static final int INT_PUSH_TYPE_MPVIDEO = 2;
	
	public static final int INT_PUSH_TYPE_MPNEWS = 3;
	
	public static final int INT_PUSH_TYPE_TEXT = 4;
	
	public static final int INT_PUSH_TYPE_WXCARD = 5;
	
	/**
	 * 根据标签进行群发【订阅号与服务号认证后均可用】或者全发	POST
	 * https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN
	 */
	public static final String MESSAGE_MASS_SENDALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
	
	/**
	 * 根据OpenID列表群发【订阅号不可用，服务号认证后可用】	POST
	 * https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN
	 */
	public static final String MESSAGE_MASS_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
	
	/**
	 * 预览接口【订阅号与服务号认证后均可用】	POST
	 * https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN
	 */
	public static final String MESSAGE_MASS_PREVIEW = "https://api.weixin.qq.com/cgi-bin/message/mass/preview";
	
	/**
	 * 预览的账号类型：微信号
	 */
	public static final int PUSH_PREVIEW_WECHAT_ID = 0;
	
	/**
	 * 预览的账号类型：openid
	 */
	public static final int PUSH_PREVIEW_OPEN_ID = 1;
	//--------------------------发送消息-群发接口和原创校验（结束）------------------------------------------//
	
	//===========================================消息管理模块（结束）===================================================//
	
	
	
	
	
	
	
	//===========================================用户管理模块（开始）===================================================//
	
	//--------------------------用户标签管理（开始）------------------------------------------//
	/**
	 * 创建标签	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_CREATE = "https://api.weixin.qq.com/cgi-bin/tags/create";
	
	/**
	 * 获取所有标签	GET
	 * https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_GET = "https://api.weixin.qq.com/cgi-bin/tags/get";
	
	/**
	 * 编辑标签	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_UPDATE = "https://api.weixin.qq.com/cgi-bin/tags/update";
	
	/**
	 * 删除标签	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_DELETE = "https://api.weixin.qq.com/cgi-bin/tags/delete";
	
	/**
	 * 获取标签下粉丝列表	GET
	 * https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN
	 */
	public static final String USER_TAG_GET = "https://api.weixin.qq.com/cgi-bin/user/tag/get";
	
	/**
	 * 批量为用户打标签	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_MEMBERS_BATCH_TAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";
	
	
	/**
	 * 批量为用户取消标签	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_MEMBERS_BATCH_UNTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";
	
	/**
	 * 获取用户身上的标签列表	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_GET_ID_LIST = "https://api.weixin.qq.com/cgi-bin/tags/getidlist";
	//--------------------------用户标签管理（结束）------------------------------------------//
	
	//--------------------------设置用户备注名（开始）------------------------------------------//
	/**
	 * 设置用户备注名	POST
	 * https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN
	 */
	public static final String USER_INFO_UPDATE_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
	//--------------------------设置用户备注名（结束）------------------------------------------//
	
	//--------------------------获取用户基本信息(UnionID机制)（开始）------------------------------------------//
	/**
	 * 获取用户基本信息（包括UnionID机制）get
	 * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN 
	 */
	public static final String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/**
	 * 批量获取用户基本信息	POST
	 * https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN 
	 */
	public static final String USER_INFO_BATCH_GET = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
	//--------------------------获取用户基本信息(UnionID机制)（结束）------------------------------------------//
	
	//--------------------------获取用户列表（开始）------------------------------------------//
	/**
	 * 获取用户列表	GET
	 * https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
	 */
	public static final String USER_GET = "https://api.weixin.qq.com/cgi-bin/user/get";
	//--------------------------获取用户列表（结束）------------------------------------------//
	
	//--------------------------黑名单管理（开始）------------------------------------------//
	/**
	 * 获取公众号的黑名单列表	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_MEMBERS_GET_BLACK_LIST = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist";
	
	/**
	 * 拉黑用户	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_MEMBERS_BATCH_BLACK_LIST = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist";
	
	/**
	 * 取消拉黑用户	POST
	 * https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=ACCESS_TOKEN
	 */
	public static final String TAGS_MEMBERS_BATCH_UNBLACK_LIST = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist";
	//--------------------------黑名单管理（结束）------------------------------------------//
	
	//===========================================用户管理模块（结束）===================================================//
	
	
	
	
	
	
	
	//===========================================素材管理模块（开始）===================================================//
	
	//媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）	
	public final static String MEDIA_TYPE_THUMB = "thumb";
	
	public final static String MEDIA_TYPE_IMAGE = "image";
	
	public final static String MEDIA_TYPE_VOICE = "voice";
	
	public final static String MEDIA_TYPE_VIDEO = "video";
	
	public final static String MEDIA_TYPE_NEWS = "news";
	
	public final static int INT_MEDIA_TYPE_THUMB = -1;
	
	public final static int INT_MEDIA_TYPE_IMAGE = 0;
	
	public final static int INT_MEDIA_TYPE_VOICE = 1;
	
	public final static int INT_MEDIA_TYPE_VIDEO = 2;
	
	public final static int INT_MEDIA_TYPE_NEWS = 3;
	
	//--------------------------新增临时素材（开始）------------------------------------------//
	/**
	 * 临时素材上传地址	POST/FORM
	 * https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
	 */
	public final static String MEDIA_UPLOAD = "https://api.weixin.qq.com/cgi-bin/media/upload";
	//--------------------------新增临时素材（结束）------------------------------------------//
	
	//--------------------------获取临时素材（开始）------------------------------------------//
	/**
	 * 获取临时素材	GET
	 * https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
	 */
	public final static String MEDIA_GET = "https://api.weixin.qq.com/cgi-bin/media/get";
	//--------------------------获取临时素材（结束）------------------------------------------//
	
	//--------------------------新增永久素材（开始）------------------------------------------//
	/**
	 * 新增永久图文素材	POST
	 * https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN
	 */
	public final static String MATERIAL_ADD_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news";
	
	/**
	 * 上传图文消息内的图片获取URL 	POST
	 * https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN
	 */
	public final static String MEDIA_UPLOAD_IMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
	
	/**
	 * 新增其他类型永久素材	POST
	 * https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE
	 */
	public final static String MATERIAL_ADD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material";
	
	//--------------------------新增永久素材（结束）------------------------------------------//
	
	//--------------------------获取永久素材（开始）------------------------------------------//
	/**
	 * 获取永久素材	POST
	 * https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN
	 */
	public final static String MATERIAL_GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material";
	//--------------------------获取永久素材（结束）------------------------------------------//
	
	//--------------------------删除永久素材（开始）------------------------------------------//
	/**
	 * 删除永久素材	POST
	 * https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN
	 */
	public final static String MATERIAL_DEL_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material";
	//--------------------------删除永久素材（结束）------------------------------------------//
	
	//--------------------------修改永久图文素材（开始）------------------------------------------//
	/**
	 * 修改永久图文素材	POST
	 * https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN
	 */
	public final static String MATERIAL_UPDATE_NEWS = "https://api.weixin.qq.com/cgi-bin/material/update_news";
	//--------------------------修改永久图文素材（结束）------------------------------------------//
	
	//--------------------------获取素材总数（开始）------------------------------------------//
	/**
	 * 获取永久素材总数	GET
	 * https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN
	 */
	public final static String MATERIAL_COUNT = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
	//--------------------------获取素材总数（结束）------------------------------------------//
	
	//--------------------------获取素材列表（开始）------------------------------------------//
	/**
	 * 获取素材列表	POST
	 * https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN
	 */
	public final static String MATERIAL_BATCH_GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
	//--------------------------获取素材列表（结束）------------------------------------------//
	
	//===========================================素材管理模块（结束）===================================================//
	
	
	
	
	
	
	
	//===========================================数据统计模块（开始）===================================================//
	
	//--------------------------用户分析数据接口（开始）------------------------------------------//
	/**
	 * 获取用户增减数据	POST
	 * https://api.weixin.qq.com/datacube/getusersummary?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_USER_SUMMARY = "https://api.weixin.qq.com/datacube/getusersummary";
	
	/**
	 * 获取累计用户数据	POST
	 * https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_USER_CUMULATE = "https://api.weixin.qq.com/datacube/getusercumulate";
	//--------------------------用户分析数据接口（结束）------------------------------------------//
	
	//--------------------------图文分析数据接口（开始）------------------------------------------//
	/**
	 * 获取图文群发每日数据	POST
	 * https://api.weixin.qq.com/datacube/getarticlesummary?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_ARTICLE_SUMMARY = "https://api.weixin.qq.com/datacube/getarticlesummary";
	
	/**
	 * 获取图文群发总数据	POST
	 * https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_ARTICLE_TOTAL = "https://api.weixin.qq.com/datacube/getarticletotal";
	
	/**
	 * 获取图文统计数据	POST
	 * https://api.weixin.qq.com/datacube/getuserread?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_USER_READ = "https://api.weixin.qq.com/datacube/getuserread";
	
	/**
	 * 获取图文统计分时数据	POST
	 * https://api.weixin.qq.com/datacube/getuserreadhour?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_USER_READ_HOUR = "https://api.weixin.qq.com/datacube/getuserreadhour";
	
	/**
	 * 获取图文分享转发数据	POST
	 * https://api.weixin.qq.com/datacube/getusershare?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_USER_SHARE = "https://api.weixin.qq.com/datacube/getusershare";
	
	/**
	 * 获取图文分享转发分时数据	POST
	 * https://api.weixin.qq.com/datacube/getusersharehour?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_USER_SHARE_HOUR = "https://api.weixin.qq.com/datacube/getusersharehour";
	
	//--------------------------图文分析数据接口（结束）------------------------------------------//
	
	//--------------------------消息分析数据接口（开始）------------------------------------------//
	
	/**
	 * 获取消息发送概况数据	POST
	 * https://api.weixin.qq.com/datacube/getupstreammsg?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_UPSTREAM_MSG = "https://api.weixin.qq.com/datacube/getupstreammsg";
	
	/**
	 * 获取消息分送分时数据	POST
	 * https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_UPSTREAM_MSG_HOUR = "https://api.weixin.qq.com/datacube/getupstreammsghour";
	
	/**
	 * 获取消息发送周数据	POST
	 * https://api.weixin.qq.com/datacube/getupstreammsgweek?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_UPSTREAM_MSG_WEEK = "https://api.weixin.qq.com/datacube/getupstreammsgweek";
	
	/**
	 * 获取消息发送月数据	POST
	 * https://api.weixin.qq.com/datacube/getupstreammsgmonth?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_UPSTREAM_MSG_MONTH = "https://api.weixin.qq.com/datacube/getupstreammsgmonth";
	
	/**
	 * 获取消息发送分布数据	POST
	 * https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_UPSTREAM_MSG_DIST = "https://api.weixin.qq.com/datacube/getupstreammsgdist";
	
	/**
	 * 获取消息发送分布周数据	POST
	 * https://api.weixin.qq.com/datacube/getupstreammsgdistweek?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_UPSTREAM_MSG_DIST_WEEK = "https://api.weixin.qq.com/datacube/getupstreammsgdistweek";
	
	/**
	 * 获取消息发送分布月数据	POST
	 * https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_UPSTREAM_MSG_DIST_MONTH = "https://api.weixin.qq.com/datacube/getupstreammsgdistmonth";
	
	//--------------------------消息分析数据接口（结束）------------------------------------------//
	
	//--------------------------接口分析数据接口（开始）------------------------------------------//
	
	/**
	 * 获取接口分析数据	POST
	 * https://api.weixin.qq.com/datacube/getinterfacesummary?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_INTERFACE_SUMMARY = "https://api.weixin.qq.com/datacube/getinterfacesummary";
	
	/**
	 * 获取接口分析分时数据	POST
	 * https://api.weixin.qq.com/datacube/getinterfacesummaryhour?access_token=ACCESS_TOKEN
	 */
	public static final String DATA_CUBE_INTERFACE_SUMMARY_HOUR = "https://api.weixin.qq.com/datacube/getinterfacesummaryhour";
	
	//--------------------------接口分析数据接口（结束）------------------------------------------//
	
	//===========================================数据统计模块（结束）===================================================//
		
} 

