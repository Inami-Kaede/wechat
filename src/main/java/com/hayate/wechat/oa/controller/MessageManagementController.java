package com.hayate.wechat.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hayate.wechat.oa.config.WeChatOaConfig;
import com.hayate.wechat.oa.service.MessageManagementService;

@Api(tags="微信公众号-消息管理")
@Controller
@RequestMapping(value = "wechat/oa/massagemanagement")
public class MessageManagementController extends BaseController{
	
	@Autowired
	private MessageManagementService messageManagementService;

	@ApiOperation(value = "群发-根据标签进行群发或者群发给所有用户") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "当消息类型为：text时此处为：content	当消息类型为：wxcard时此处为：card_id",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "pushAll", value = "true发送给所有用户 false发送给指定标签用户",required = true, dataType = "Boolean", paramType = "query"),
		@ApiImplicitParam(name = "tagId", value = "标签ID",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "type", value = "消息类型(0:图片 1:语音 2:视频  3:图文 4:文本 5:卡券 )",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "sendIgnoreReprint", value = "设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。为0时，文章被判定为转载时，将停止群发操作。",required = true, dataType = "Integer", paramType = "query")
	})
	@RequestMapping(value="tag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pushMessageByTagOrPushAll(@RequestParam(required=true)String mediaId,@RequestParam(required=true)boolean pushAll,@RequestParam(required=true)int tagId,
			@RequestParam(required=true)int type,@RequestParam(required=true)int sendIgnoreReprint){
		
		String log = "群发-根据标签进行群发或者群发给所有用户";
		logger.info("收到"+log+"请求");
		String msgType = null;
		if(type == WeChatOaConfig.INT_PUSH_TYPE_IMAGE){
			msgType = WeChatOaConfig.PUSH_TYPE_IMAGE;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_VOICE){
			msgType = WeChatOaConfig.PUSH_TYPE_VOICE;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_MPVIDEO){
			msgType = WeChatOaConfig.PUSH_TYPE_MPVIDEO;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_MPNEWS){
			msgType = WeChatOaConfig.PUSH_TYPE_MPNEWS;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_TEXT){
			msgType = WeChatOaConfig.PUSH_TYPE_TEXT;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_WXCARD){
			msgType = WeChatOaConfig.PUSH_TYPE_WXCARD;
		}
		
		return messageManagementService.pushMessageByTagOrPushAll(mediaId,pushAll,tagId,msgType,sendIgnoreReprint);
	}
	
	@ApiOperation(value = "群发-根据openid进行群发") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "当消息类型为：text时此处为：content	当消息类型为：wxcard时此处为：card_id",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "openIds", value = "用户的openId集合",required = true, dataType = "Model", paramType = "body"),
		@ApiImplicitParam(name = "type", value = "消息类型(0:图片 1:语音 2:视频  3:图文 4:文本 5:卡券 )",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "sendIgnoreReprint", value = "设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。为0时，文章被判定为转载时，将停止群发操作。",required = true, dataType = "Integer", paramType = "query")
	})
	@RequestMapping(value="openid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pushMessageByOpenIds(@RequestParam(required=true)String mediaId,@RequestBody List<String> openIds,
			@RequestParam(required=true)int type,@RequestParam(required=true)int sendIgnoreReprint){
		
		String log = "群发-根据openid进行群发";
		logger.info("收到"+log+"请求");
		String msgType = null;
		if(type == WeChatOaConfig.INT_PUSH_TYPE_IMAGE){
			msgType = WeChatOaConfig.PUSH_TYPE_IMAGE;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_VOICE){
			msgType = WeChatOaConfig.PUSH_TYPE_VOICE;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_MPVIDEO){
			msgType = WeChatOaConfig.PUSH_TYPE_MPVIDEO;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_MPNEWS){
			msgType = WeChatOaConfig.PUSH_TYPE_MPNEWS;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_TEXT){
			msgType = WeChatOaConfig.PUSH_TYPE_TEXT;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_WXCARD){
			msgType = WeChatOaConfig.PUSH_TYPE_WXCARD;
		}
		
		return messageManagementService.pushMessageByOpenIds(mediaId, openIds, msgType, sendIgnoreReprint);
	}
	
	@ApiOperation(value = "群发-消息预览") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "当消息类型为：text时此处为：content	当消息类型为：wxcard时此处为：card_id",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "type", value = "消息类型(0:图片 1:语音 2:视频  3:图文 4:文本 5:卡券 )",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "toUserType", value = "账号类型(0:微信号 1：openId)",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "toUser", value = "用于预览的账号",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="preview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pushMessagePreview(@RequestParam(required=true)String mediaId,@RequestParam(required=true)int type,
			@RequestParam(required=true)int toUserType,@RequestParam(required=true)String toUser){
		
		String log = "群发-消息预览";
		logger.info("收到"+log+"请求");
		String msgType = null;
		if(type == WeChatOaConfig.INT_PUSH_TYPE_IMAGE){
			msgType = WeChatOaConfig.PUSH_TYPE_IMAGE;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_VOICE){
			msgType = WeChatOaConfig.PUSH_TYPE_VOICE;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_MPVIDEO){
			msgType = WeChatOaConfig.PUSH_TYPE_MPVIDEO;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_MPNEWS){
			msgType = WeChatOaConfig.PUSH_TYPE_MPNEWS;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_TEXT){
			msgType = WeChatOaConfig.PUSH_TYPE_TEXT;
		}else if(type == WeChatOaConfig.INT_PUSH_TYPE_WXCARD){
			msgType = WeChatOaConfig.PUSH_TYPE_WXCARD;
		}
		
		return messageManagementService.pushMessagePreview(toUserType, toUser, mediaId, msgType);
	}
}
