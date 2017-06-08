package com.hayate.wechat.oa.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.oa.service.MessageManagementService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
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
}
