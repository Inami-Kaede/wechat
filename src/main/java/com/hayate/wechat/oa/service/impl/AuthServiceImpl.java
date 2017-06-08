package com.hayate.wechat.oa.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.oa.service.AuthService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class AuthServiceImpl extends BaseService implements AuthService {

	/** 
	 * 根据获取的code换取userAccessToken 再用userAccessToken换取用户信息（公众号范围）
	 */
	@Override
	public Map<String,Object> getUserInfoByCode(String code) {
				
		Map<String, Object> userAccessToken = getUserAccessToken(code);
		String accessToken = (String) userAccessToken.get("access_token");
		String openId = (String) userAccessToken.get("openid");
		String refreshToken = (String) userAccessToken.get("refresh_token");
		
			
		if(!StringUtils.isEmpty(accessToken) && !StringUtils.isEmpty(openId)){
			
			checkUserAccessToken(accessToken, openId);
			
			Map<String,Object> userInfo = getUserInfo(accessToken, openId);
			
			refreshUserAccessToken(refreshToken);
			
			return userInfo;
		}
		
		return null;
	}	
	
	/**
	 * 获取微信（公众号）登录跳转链接
	 * @param state	额外参数（非必须）
	 * @param type 	0 	scope 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）， 
	 * 				1	snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，
	 * 					即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @return	
	 */
	@Override
	public String getAuthUrl(String state,int scope){
				
		String url = WeChatOaConfig.OAUTH2_AUTHORIZE;
		
		Map<String,String> param = new LinkedHashMap<String,String>();
		param.put("appid", WeChatOaConfig.APP_ID);
		param.put("redirect_uri",WeChatOaConfig.REDIRECT_URI);
		param.put("response_type", "code");
		if(scope == 0){
			param.put("scope", WeChatOaConfig.LOGIN_SCOPE_BASE);
		}else if(scope == 1){
			param.put("scope", WeChatOaConfig.LOGIN_SCOPE_USERINFO);
		}else{
			throw new RuntimeException("scope类型错误");
		}
		if(!StringUtils.isEmpty(state)){
			param.put("state", state);
		}
		
		String createUrl = HttpClientUtil.createUrl(url, param);
		
		return createUrl+"#wechat_redirect";
	}
	
	/**
	 * 获取用户的accessToken
	 * @param code	获取到的code
	 * @return	返回结果	{ "access_token":"ACCESS_TOKEN",    
	 *					  "expires_in":7200,    
	 *					  "refresh_token":"REFRESH_TOKEN",    
	 * 					  "openid":"OPENID",    
	 *					  "scope":"SCOPE" } 
	 */
	@Override
	public Map<String,Object> getUserAccessToken(String code){
		
		String log = "请求用户accessToken";
		
		String url = WeChatOaConfig.OAUTH2_ACCESS_TOKEN;
    	
		Map<String,String> param = new LinkedHashMap<String,String>();
    	param.put("appid", WeChatOaConfig.APP_ID);
    	param.put("secret", WeChatOaConfig.APP_SECRET);   	
    	param.put("code", code);
    	param.put("grant_type", "authorization_code");
    	String stringResult = HttpClientUtil.doGet(url, param);
    	Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
    	
    	logger.debug(log+"返回数据（开始）");
    	for(Entry e : mapResult.entrySet()){
    		logger.debug(e.getKey()+":"+e.getValue());
    	}
    	logger.debug(log+"返回数据（结束）");
    	
    	return mapResult;
	}
	
	/**
	 * @param refreshToken
	 * @return	{ "access_token":"ACCESS_TOKEN",  
	 *		 	 "expires_in":7200,   
	 *			 "refresh_token":"REFRESH_TOKEN",   
	 *			 "openid":"OPENID",   
	 *			 "scope":"SCOPE" } 
	 */
	@Override
	public Map refreshUserAccessToken(String refreshToken){
		
		String log = "请求刷新用户accessToken";
		
		String url = WeChatOaConfig.OAUTH2_REFRESH_TOKEN;
		
		Map<String,String> param = new LinkedHashMap<String,String>();
    	param.put("appid", WeChatOaConfig.APP_ID);
    	param.put("grant_type", "refresh_token");   	
    	param.put("refresh_token", refreshToken);
    	String stringResult = HttpClientUtil.doGet(url, param);
    	Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
    	
    	logger.debug(log+"返回数据（开始）");
    	for(Entry e : mapResult.entrySet()){
    		logger.debug(e.getKey()+":"+e.getValue());
    	}
    	logger.debug(log+"返回数据（结束）");
    	
    	return mapResult;
		
	}
	
	/**
	 * @param userAccessToken
	 * @param openId
	 * @return			{    "openid":" OPENID",  
	 *	用户昵称				 " nickname": NICKNAME,   
	 *				 		 "sex":"1",   
	 *	用户个人资料填写的省份		 "province":"PROVINCE"   
	 *						 "city":"CITY", 
	 *	国家，如中国为CN		 	 "country":"COUNTRY",  
	 *	
	 *	用户头像，最后一个数值代表正方形头像大小
	 *	（有0、46、64、96、132数值可选，0代表640*640正方形头像），
	 *	用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。			   
	 *	"headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ
	 *								4eMsv84eavHiaiceqxibJxCfHe/46",  
	 *	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）			 "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],    
	 *	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。			 "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" } 
	 */
	@Override
	public Map<String,Object> getUserInfo(String userAccessToken,String openId){
		
		String log = "请求用户信息";
		
		String url = WeChatOaConfig.SNS_USER_INFO;
		
		Map<String,String> param = new LinkedHashMap<String,String>();
    	param.put("access_token", userAccessToken);
    	param.put("openid", openId);   	
    	param.put("lang", "zh_CN");
    	String stringResult = HttpClientUtil.doGet(url, param);
    	Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
    	
    	logger.debug(log+"返回数据（开始）");
    	for(Entry<String,Object> e : mapResult.entrySet()){
    		logger.debug(e.getKey()+":"+e.getValue());
    	}
    	logger.debug(log+"返回数据（结束）");
    	
    	return mapResult;
	}
	
	/**
	 * @param accessToken
	 * @param openId
	 * @return	正确的JSON返回结果：
	 *			{ "errcode":0,"errmsg":"ok"} 
	 *			错误时的JSON返回示例：
	 *			{ "errcode":40003,"errmsg":"invalid openid"}
	 */
	@Override
	public Map checkUserAccessToken(String accessToken,String openId){
		
		String log = "请求用户accessToken是否可用";
		
		String url = WeChatOaConfig.SNS_AUTH;
		
		Map<String,String> param = new LinkedHashMap<String,String>();
    	param.put("access_token", accessToken);
    	param.put("openid", openId);
    	String stringResult = HttpClientUtil.doGet(url, param);
    	Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
    	
    	logger.debug(log+"返回数据（开始）");
    	for(Entry e : mapResult.entrySet()){
    		logger.debug(e.getKey()+":"+e.getValue());
    	}
    	logger.debug(log+"返回数据（结束）");
    	
    	return mapResult;
		
	}
}
