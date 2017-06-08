package com.hayate.wechat.oa.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.oa.service.CustomMenuService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class CustomMenuServiceImpl extends BaseService implements
		CustomMenuService {

	@Override
	public String createMenu(String json) {
	
		String log = "创建自定义菜单";
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MENU_CREATE,params,json);
		HashMap<String, Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

		return (String)mapResult.get("errmsg");
	}
	
	@Override
	public String getMenu() {
		
		String log = "查询自定义菜单";
	
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.MENU_GET, params);
		HashMap<String, Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
       
        if(!StringUtils.isEmpty((String)mapResult.get("errmsg"))){
        	return (String)mapResult.get("errmsg");
        }
		return stringResult;

	}
	
	@Override
	public String deleteMenu() {
		
		String log = "删除自定义菜单";
	
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.MENU_DELETE, params);
		HashMap<String, Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

		return (String)mapResult.get("errmsg");

	}
	
}
