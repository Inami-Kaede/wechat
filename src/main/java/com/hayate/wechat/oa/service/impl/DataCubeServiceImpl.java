package com.hayate.wechat.oa.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.oa.service.DataCubeService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class DataCubeServiceImpl extends BaseService implements DataCubeService {
	
	/**
	 * 获取用户增减数据
	 */
	@Override
	public List<Map<String, Object>> getUserSummary(Date begin,Date end){
		
		String log = "获取用户增减数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_USER_SUMMARY, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取累计用户数据
	 */
	@Override
	public List<Map<String, Object>> getUserCumulate(Date begin,Date end){
		
		String log = "获取累计用户数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_USER_CUMULATE, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取图文群发每日数据
	 */
	@Override
	public List<Map<String, Object>> getArticleSummary(Date begin,Date end){
		
		String log = "获取图文群发每日数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_ARTICLE_SUMMARY, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取图文群发总数据
	 */
	@Override
	public List<Map<String, Object>> getArticleTotal(Date begin,Date end){
		
		String log = "获取图文群发总数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_ARTICLE_TOTAL, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取图文统计数据
	 */
	@Override
	public List<Map<String, Object>> getUserRead(Date begin,Date end){
		
		String log = "获取图文统计数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_USER_READ, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取图文统计分时数据
	 */
	@Override
	public List<Map<String, Object>> getUserReadHour(Date begin,Date end){
		
		String log = "获取图文统计分时数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_USER_READ_HOUR, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取图文分享转发数据
	 */
	@Override
	public List<Map<String, Object>> getUserShare(Date begin,Date end){
		
		String log = "获取图文分享转发数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_USER_SHARE, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取图文分享转发分时数据
	 */
	@Override
	public List<Map<String, Object>> getUserShareHour(Date begin,Date end){
		
		String log = "获取图文分享转发分时数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_USER_SHARE_HOUR, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取消息发送概况数据
	 */
	@Override
	public List<Map<String, Object>> getUpStreamMsg(Date begin,Date end){
		
		String log = "获取消息发送概况数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_UPSTREAM_MSG, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取消息分送分时数据
	 */
	@Override
	public List<Map<String, Object>> getUpStreamMsgHour(Date begin,Date end){
		
		String log = "获取消息分送分时数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_UPSTREAM_MSG_HOUR, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取消息发送周数据
	 */
	@Override
	public List<Map<String, Object>> getUpStreamMsgWeek(Date begin,Date end){
		
		String log = "获取消息发送周数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_UPSTREAM_MSG_WEEK, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取消息发送月数据
	 */
	@Override
	public List<Map<String, Object>> getUpStreamMsgMonth(Date begin,Date end){
		
		String log = "获取消息发送月数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_UPSTREAM_MSG_MONTH, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取消息发送分布数据
	 */
	@Override
	public List<Map<String, Object>> getUpStreamMsgDist(Date begin,Date end){
		
		String log = "获取消息发送分布数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_UPSTREAM_MSG_DIST, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取消息发送分布周数据
	 */
	@Override
	public List<Map<String, Object>> getUpStreamMsgDistWeek(Date begin,Date end){
		
		String log = "获取消息发送分布周数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_UPSTREAM_MSG_DIST_WEEK, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取消息发送分布月数据
	 */
	@Override
	public List<Map<String, Object>> getUpStreamMsgDistMonth(Date begin,Date end){
		
		String log = "获取消息发送分布月数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_UPSTREAM_MSG_DIST_MONTH, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取接口分析数据
	 */
	@Override
	public List<Map<String, Object>> getInterfaceSummary(Date begin,Date end){
		
		String log = "获取接口分析数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_INTERFACE_SUMMARY, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
	
	/**
	 * 获取接口分析分时数据
	 */
	@Override
	public List<Map<String, Object>> getInterfaceSummaryHour(Date begin,Date end){
		
		String log = "获取接口分析分时数据";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_date", new DateTime(begin).toString("yyyy-MM-dd"));
		bodyMap.put("end_date", new DateTime(end).toString("yyyy-MM-dd"));
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.DATA_CUBE_INTERFACE_SUMMARY_HOUR, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> listMap = (List<Map<String,Object>>) mapResult.get("list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : listMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return listMap;
	}
}
