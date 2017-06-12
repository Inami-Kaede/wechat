package com.hayate.wechat.oa.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.hayate.wechat.oa.service.MessageManagementService;

@Service
public class MessageManagementServiceImpl extends BaseService implements
		MessageManagementService {

	/**
	 * 根据标签进行群发或者群发给所有用户
	 * @param mediaId	当消息类型为：text时此处为：content	当消息类型为：wxcard时此处为：card_id
	 * @param pushAll	true发送给所有用户 false发送给指定标签用户
	 * @param tagId	
	 * @param msgType	
	 * @param sendIgnoreReprint	设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。为0时，文章被判定为转载时，将停止群发操作。
	 * @return	
	 */
	@Override
	public Map<String,Object> pushMessageByTagOrPushAll(String mediaId,boolean pushAll,int tagId,String msgType,int sendIgnoreReprint){
		
		String log = "根据标签进行群发";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
			
		Map<String,Object> filter = new LinkedHashMap<String,Object>();
		filter.put("is_to_all", pushAll);
		filter.put("tag_id", tagId);
		
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();		
		bodyMap.put("filter", filter);
		
		if(msgType.equals(WeChatOaConfig.PUSH_TYPE_MPNEWS)){
			
			Map<String,Object> mpnews = new LinkedHashMap<String,Object>();	
			mpnews.put("media_id", mediaId);
			
			bodyMap.put("mpnews", mpnews);
			bodyMap.put("msgtype", msgType);
			bodyMap.put("send_ignore_reprint", sendIgnoreReprint);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_TEXT)){
			
			Map<String,Object> text = new LinkedHashMap<String,Object>();	
			text.put("content", mediaId);
			
			bodyMap.put("text", text);
			bodyMap.put("msgtype", msgType);
			
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_VOICE)){
			
			Map<String,Object> voice = new LinkedHashMap<String,Object>();	
			voice.put("media_id", mediaId);
			
			bodyMap.put("voice", voice);
			bodyMap.put("msgtype", msgType);
			
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_IMAGE)){
			
			Map<String,Object> image = new LinkedHashMap<String,Object>();	
			image.put("media_id", mediaId);
			
			bodyMap.put("image", image);
			bodyMap.put("msgtype", msgType);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_MPVIDEO)){
			
			Map<String,Object> mpvideo = new LinkedHashMap<String,Object>();	
			mpvideo.put("media_id", mediaId);
			
			bodyMap.put("mpvideo", mpvideo);
			bodyMap.put("msgtype", msgType);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_WXCARD)){
			
			Map<String,Object> wxcard = new LinkedHashMap<String,Object>();	
			wxcard.put("card_id", mediaId);
			
			bodyMap.put("wxcard", wxcard);
			bodyMap.put("msgtype", msgType);
						
		}else{
			throw new RuntimeException("消息类型错误");
		}
				
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MESSAGE_MASS_SENDALL, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        return mapResult;
	}
	
	/**
	 * 根据openid进行群发
	 * @param mediaId	当消息类型为：text时此处为：content	当消息类型为：wxcard时此处为：card_id
	 * @param openIds	
	 * @param msgType	
	 * @param sendIgnoreReprint	设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。为0时，文章被判定为转载时，将停止群发操作。
	 * @return	
	 */
	@Override
	public Map<String,Object> pushMessageByOpenIds(String mediaId,List<String> openIds,String msgType,int sendIgnoreReprint){
		
		String log = "根据openid进行群发";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
			
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();		
		bodyMap.put("touser", openIds);
		
		if(msgType.equals(WeChatOaConfig.PUSH_TYPE_MPNEWS)){
			
			Map<String,Object> mpnews = new LinkedHashMap<String,Object>();	
			mpnews.put("media_id", mediaId);
			
			bodyMap.put("mpnews", mpnews);
			bodyMap.put("msgtype", msgType);
			bodyMap.put("send_ignore_reprint", sendIgnoreReprint);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_TEXT)){
			
			Map<String,Object> text = new LinkedHashMap<String,Object>();	
			text.put("content", mediaId);
			
			bodyMap.put("text", text);
			bodyMap.put("msgtype", msgType);
			
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_VOICE)){
			
			Map<String,Object> voice = new LinkedHashMap<String,Object>();	
			voice.put("media_id", mediaId);
			
			bodyMap.put("voice", voice);
			bodyMap.put("msgtype", msgType);
			
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_IMAGE)){
			
			Map<String,Object> image = new LinkedHashMap<String,Object>();	
			image.put("media_id", mediaId);
			
			bodyMap.put("image", image);
			bodyMap.put("msgtype", msgType);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_MPVIDEO)){
			
			Map<String,Object> mpvideo = new LinkedHashMap<String,Object>();	
			mpvideo.put("media_id", mediaId);
			
			bodyMap.put("mpvideo", mpvideo);
			bodyMap.put("msgtype", msgType);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_WXCARD)){
			
			Map<String,Object> wxcard = new LinkedHashMap<String,Object>();	
			wxcard.put("card_id", mediaId);
			
			bodyMap.put("wxcard", wxcard);
			bodyMap.put("msgtype", msgType);
						
		}else{
			throw new RuntimeException("消息类型错误");
		}
				
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MESSAGE_MASS_SEND, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        return mapResult;
	}
	
	/**
	 * 预览群发消息
	 * @param toUserType	用户账号类型：0：微信号 	1：openid
	 * @param toUser	
	 * @param mediaId	当消息类型为：text时此处为：content	当消息类型为：wxcard时此处为：card_id
	 * @param msgType
	 * @return	
	 */
	@Override
	public Map<String,Object> pushMessagePreview(int toUserType,String toUser,String mediaId,String msgType){
		
		String log = "预览群发";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
			
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();	
		if(toUserType == 0){
			bodyMap.put("towxname", toUser);
		}else if(toUserType == 1){
			bodyMap.put("touser", toUser);
		}else{
			throw new RuntimeException("用户类型错误!");
		}
		
		
		if(msgType.equals(WeChatOaConfig.PUSH_TYPE_MPNEWS)){
			
			Map<String,Object> mpnews = new LinkedHashMap<String,Object>();	
			mpnews.put("media_id", mediaId);
			
			bodyMap.put("mpnews", mpnews);
			bodyMap.put("msgtype", msgType);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_TEXT)){
			
			Map<String,Object> text = new LinkedHashMap<String,Object>();	
			text.put("content", mediaId);
			
			bodyMap.put("text", text);
			bodyMap.put("msgtype", msgType);
			
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_VOICE)){
			
			Map<String,Object> voice = new LinkedHashMap<String,Object>();	
			voice.put("media_id", mediaId);
			
			bodyMap.put("voice", voice);
			bodyMap.put("msgtype", msgType);
			
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_IMAGE)){
			
			Map<String,Object> image = new LinkedHashMap<String,Object>();	
			image.put("media_id", mediaId);
			
			bodyMap.put("image", image);
			bodyMap.put("msgtype", msgType);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_MPVIDEO)){
			
			Map<String,Object> mpvideo = new LinkedHashMap<String,Object>();	
			mpvideo.put("media_id", mediaId);
			
			bodyMap.put("mpvideo", mpvideo);
			bodyMap.put("msgtype", msgType);
						
		}else if(msgType.equals(WeChatOaConfig.PUSH_TYPE_WXCARD)){
			
			Map<String,Object> wxcard = new LinkedHashMap<String,Object>();	
			wxcard.put("card_id", mediaId);
			
			bodyMap.put("wxcard", wxcard);
			bodyMap.put("msgtype", msgType);
						
		}else{
			throw new RuntimeException("消息类型错误");
		}
				
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MESSAGE_MASS_PREVIEW, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        return mapResult;
	}
	
	/** 
	 * 设置所属行业
	 */
	@Override
	public Map<String,Object> setIndustry(int industryId1,int industryId2){
		
		String log = "设置所属行业";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("industry_id1", industryId1);
		bodyMap.put("industry_id2", industryId2);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TEMPLATE_SET_INDUSTRY, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        return mapResult;
	}
	
	/** 
	 * 获取设置的行业信息
	 */
	@Override
	public Map<String,Object> getIndustry(){
		
		String log = "获取设置的行业信息";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());

		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.TEMPLATE_GET_INDUSTRY, params);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        return mapResult;
	}
	
	/** 
	 * 获得模板ID
	 */
	@Override
	public Map<String,Object> addTemplate(String templateIdShort){
		
		String log = "获得模板ID";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("template_id_short", templateIdShort);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TEMPLATE_ADD_TEMPLATE, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        return mapResult;
	}
	
	/** 
	 * 获取模板列表
	 */
	@Override
	public List<Map<String,Object>> getAllPrivateTemplate(){
		
		String log = "获取模板列表";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());

		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.TEMPLATE_GET_ALL_PRIVATE_TEMPLATE, params);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> list = (List<Map<String, Object>>) mapResult.get("template_list");
        
        logger.debug(log+"返回信息2（开始）");
        
        for (Map<String, Object> map : list) {			
        	for(Entry<String,Object> e : map.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());       	
        	}
		}
        logger.debug("----------------------");
        logger.debug(log+"返回信息2（结束）");

        return list;
	}
	
	/** 
	 * 删除模板
	 */
	@Override
	public Map<String,Object> delPrivateTemplate(String templateId){
		
		String log = "删除模板";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("template_id", templateId);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TEMPLATE_DEL_PRIVATE_TEMPLATE, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        return mapResult;
	}
	
	/** 
	 * 发送模板消息
	 */
	@Override
	public Map<String,Object> send(String touser,String templateId,String url,Map<String,Object> miniprogram,Map<String,Object> data){
		
		String log = "发送模板消息";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("touser", touser);
		bodyMap.put("template_id", templateId);
		bodyMap.put("url", url);
		bodyMap.put("miniprogram", miniprogram);
		bodyMap.put("data", data);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TEMPLATE_SEND, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        return mapResult;
	}
}
