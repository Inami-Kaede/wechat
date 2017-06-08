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
import com.hayate.wechat.oa.service.AccountManagementService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class AccountManagementServiceImpl extends BaseService implements AccountManagementService{

	/**
	 * 创建带参二维码
	 * @param userId	
	 * @param type	0 临时消息 1永久消息 2永久消息（消息内容为String类型）
	 * {"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
	 * @return
	 */
	@Override
	public String createParamQrcode(String userId,int type) {
		
		String log = "创建带参二维码";
		
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("access_token", getAccessToken());
		
		Map<String,Object> sceneStr = new LinkedHashMap<String,Object>();
		if(type == 0 || type == 1){
			sceneStr.put("scene_str", Integer.parseInt(userId));
		}else if(type == 2){
			sceneStr.put("scene_str", userId);
		}else{
			throw new RuntimeException("type类型错误");
		}
			
		Map<String,Object> scene = new LinkedHashMap<String,Object>();
		scene.put("scene", sceneStr);
		
		Map<String,Object> body = new LinkedHashMap<String,Object>();
		if(type == 0){
			body.put("expire_seconds", WeChatOaConfig.QRCODE_EXPIRE);
			body.put("action_name", "QR_SCENE");
		}else if(type == 1){
			body.put("action_name", "QR_LIMIT_SCENE");
		}else{
			body.put("action_name", "QR_LIMIT_STR_SCENE");			
		}
		body.put("action_info", scene);
		
		
		String json = JsonUtils.objectToJson(body);
		logger.debug("转换后的json："+json);
		
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.QRCODE_CREATE,params, json);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		logger.debug(log+"返回的参数（开始）");
		
		for(Entry<String,Object> e : mapResult.entrySet()){
			logger.debug(e.getKey()+":"+e.getValue());
		}
		logger.debug(log+"返回的参数（结束）");
				
		return (String) mapResult.get("url");
	}
	
	/** 
	 * 创建短链接
	 */
	@Override
	public String getShortUrl(String url){
		
		String log = "创建短链接";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("action", "long2short");
		bodyMap.put("long_url", url);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.SHORT_URL, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        String shortUrl = (String) mapResult.get("short_url"); 
        
        if(!StringUtils.isEmpty(shortUrl)){
        	return shortUrl;
        }

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
}
