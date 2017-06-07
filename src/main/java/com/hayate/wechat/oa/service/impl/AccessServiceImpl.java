package com.hayate.wechat.oa.service.impl;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hayate.wechat.common.util.CommonUtil;
import com.hayate.wechat.common.util.MapUtil;
import com.hayate.wechat.common.util.MessageUtil;
import com.hayate.wechat.oa.config.WeChatOaConfig;
import com.hayate.wechat.oa.pojo.receive.BaseReceiveEvent;
import com.hayate.wechat.oa.pojo.receive.ReceiveEventCustom;
import com.hayate.wechat.oa.pojo.receive.ReceiveEventLocation;
import com.hayate.wechat.oa.pojo.receive.ReceiveEventScan;
import com.hayate.wechat.oa.pojo.receive.ReceiveMessageImage;
import com.hayate.wechat.oa.pojo.receive.ReceiveMessageLink;
import com.hayate.wechat.oa.pojo.receive.ReceiveMessageLocation;
import com.hayate.wechat.oa.pojo.receive.ReceiveMessageText;
import com.hayate.wechat.oa.pojo.receive.ReceiveMessageVideo;
import com.hayate.wechat.oa.pojo.receive.ReceiveMessageVoice;
import com.hayate.wechat.oa.pojo.send.Image;
import com.hayate.wechat.oa.pojo.send.Item;
import com.hayate.wechat.oa.pojo.send.Music;
import com.hayate.wechat.oa.pojo.send.SendMessageImage;
import com.hayate.wechat.oa.pojo.send.SendMessageMusic;
import com.hayate.wechat.oa.pojo.send.SendMessageNews;
import com.hayate.wechat.oa.pojo.send.SendMessageText;
import com.hayate.wechat.oa.pojo.send.SendMessageVideo;
import com.hayate.wechat.oa.pojo.send.SendMessageVoice;
import com.hayate.wechat.oa.pojo.send.Video;
import com.hayate.wechat.oa.pojo.send.Voice;
import com.hayate.wechat.oa.service.AccessService;
import com.hayate.wechat.oa.service.AccountManagementService;
import com.hayate.wechat.oa.service.MaterialManagementService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class AccessServiceImpl extends BaseService implements AccessService {
	
	@Autowired
	private AccountManagementService accountManagementService;
	
	@Autowired
	private MaterialManagementService materialManagementService;
	
	@Override
	public String receiveMsg(Map<String, Object> param) {
		
		String msgType = (String) param.get("msgType");
		String toUserName = (String) param.get("fromUserName");
		String fromUserName = (String) param.get("toUserName");
		int createTime = Integer.parseInt(String.valueOf(param.get("createTime")).substring(0,8));
		
		if(msgType.equals("text")){
			
			logger.debug("收到文本消息");
			ReceiveMessageText text = (ReceiveMessageText) MapUtil.mapToObject(param, ReceiveMessageText.class);
			
			String content = text.getContent();
			try{
				int i = Integer.parseInt(content);
				return messagePackage(i, toUserName, fromUserName, createTime);
			}catch(Exception e){
				
			}
			
		}else if(msgType.equals("image")){
			
			logger.debug("收到图片消息");
			ReceiveMessageImage image = (ReceiveMessageImage) MapUtil.mapToObject(param, ReceiveMessageImage.class);
			
		}else if(msgType.equals("voice")){
			
			logger.debug("收到语音消息");
			ReceiveMessageVoice voice = (ReceiveMessageVoice) MapUtil.mapToObject(param, ReceiveMessageVoice.class);
			
		}else if(msgType.equals("video")){
			
			logger.debug("收到视频消息");
			ReceiveMessageVideo video = (ReceiveMessageVideo) MapUtil.mapToObject(param, ReceiveMessageVideo.class);
			
		}else if(msgType.equals("shortvideo")){
			
			logger.debug("收到小视频消息");
			ReceiveMessageVideo shortvideo = (ReceiveMessageVideo) MapUtil.mapToObject(param, ReceiveMessageVideo.class);
			
		}else if(msgType.equals("location")){
			
			logger.debug("收到地理位置消息");
			ReceiveMessageLocation location = (ReceiveMessageLocation) MapUtil.mapToObject(param, ReceiveMessageLocation.class);
			
		}else if(msgType.equals("link")){
			
			logger.debug("收到链接消息");
			ReceiveMessageLink link = (ReceiveMessageLink) MapUtil.mapToObject(param, ReceiveMessageLink.class);
			
		}else if(msgType.equals("event")){
			
			String event = (String)param.get("event");
			
			//订阅事件
			if(event.equals("subscribe")){
						
				String eventKey = (String) param.get("eventKey");
				if(!StringUtils.isEmpty(eventKey)){	//未关注公众号的扫码事件
					
					logger.debug("收到扫描带参数二维码事件（未关注）");
					String newEventKey = eventKey.substring(eventKey.indexOf("_")+1);
					logger.debug("收到的EventKey:"+eventKey);
					logger.debug("截取后的EventKey:"+newEventKey);
					param.put("eventKey", newEventKey);					
					ReceiveEventScan newScan = (ReceiveEventScan) MapUtil.mapToObject(param, ReceiveEventScan.class);
										
				}else{
					
					logger.debug("收到关注事件");
					BaseReceiveEvent subscribe = (BaseReceiveEvent) MapUtil.mapToObject(param, BaseReceiveEvent.class);
					
				}
				
			}else if(event.equals("unsubscribe")){	//取消订阅
				
				logger.debug("收到取消关注事件");
				BaseReceiveEvent unsubscribe = (BaseReceiveEvent) MapUtil.mapToObject(param, BaseReceiveEvent.class);
				
							
			}else if(event.equals("SCAN")){	//已关注公众号的扫码事件
				
				logger.debug("收到扫描带参数二维码事件（已关注）");
				ReceiveEventScan scan = (ReceiveEventScan) MapUtil.mapToObject(param, ReceiveEventScan.class);
				logger.debug("收到的EventKey:"+scan.getEventKey());
				
			}else if(event.equals("LOCATION")){	//上报地理位置事件
				
				logger.debug("收到上报地理位置事件");
				ReceiveEventLocation location = (ReceiveEventLocation) MapUtil.mapToObject(param, ReceiveEventLocation.class);
				
			}else if(event.equals("CLICK")){	//点击菜单拉取消息时的事件
				
				logger.debug("收到点击菜单拉取消息时的事件");
				ReceiveEventCustom click = (ReceiveEventCustom) MapUtil.mapToObject(param, ReceiveEventCustom.class);
				//如果触发了获取二维码事件
				if(click.getEventKey().equals(WeChatOaConfig.EVENT_KEY_QRCODE)){
					
					String url = accountManagementService.createParamQrcode(toUserName, 2);					
					byte[] data = CommonUtil.createQrcode(url, WeChatOaConfig.QRCODE_WIDTH, WeChatOaConfig.QRCODE_HEIGHT);					
					String mediaId = materialManagementService.uploadTemp(WeChatOaConfig.MEDIA_TYPE_IMAGE, data, "jpg");
					
					return MessageUtil.imageMessageToXml(new SendMessageImage(toUserName, fromUserName, createTime, new Image(mediaId)));
				}else if(click.getEventKey().equals("news")){
					Item item1 = new Item("https://www.baidu.com/", "http://mmbiz.qpic.cn/mmbiz_jpg/dyCj6wiceMoTQOpSx7YicV4gHHxUu2A7cib7kqicyaMnicbuCtTc5Mr56WVjbSibsNQHobzERKTJ8Q0NIQeEGNl3vCCg/0?wx_fmt=jpeg", "我是图文标题1", "我是图文描述1");
					Item[] articles = new Item[]{item1};			
					String articleCount = String.valueOf(articles.length);
					
					return MessageUtil.newsMessageToXml(new SendMessageNews(toUserName, fromUserName, createTime, articleCount, articles));
				}
				
			}else if(event.equals("VIEW")){	//点击菜单跳转链接时的事件
				
				logger.debug("收到点击菜单跳转链接时的事件");
				ReceiveEventCustom view = (ReceiveEventCustom) MapUtil.mapToObject(param, ReceiveEventCustom.class);
				
			}else{
				
				logger.error("未知的事件类型！当前事件类型："+event);
				return null;			
			}
			
		}else{
			
			logger.error("未知的消息类型！当前消息类型："+msgType);
			return null;			
		}
		Random r = new Random();		
		int index = r.nextInt(6);
		//int index = 4;
		String result = messagePackage(index,toUserName,fromUserName,createTime);
		//logger.debug("当前随机数："+index);
		logger.debug("返回随机消息："+result);
		return result;
	}

	private String messagePackage(int nextInt,String toUserName, String fromUserName,int createTime) {
	
		String result = null;
		
		switch (nextInt) {
		
		
		//回复文字消息
		case 0:
			String content = "我是随机消息喔~";
			result = MessageUtil.textMessageToXml(new SendMessageText(toUserName, fromUserName, createTime, content));
			logger.debug("返回的消息："+result);
			return result;
		
		//回复图片消息
		case 1:
			Image image = new Image("txuR4p9wczA-bGvEkmU7lYy3Dt6fOTNc0nQM-ktE0n4");
			result = MessageUtil.imageMessageToXml(new SendMessageImage(toUserName, fromUserName, createTime, image));
			logger.debug("返回的消息："+result);
			return result;
			
		//回复语音消息
		case 2:
			Voice voice = new Voice("txuR4p9wczA-bGvEkmU7lVH_E8SoZWzXhEHxudlB2rk");
			result = MessageUtil.voiceMessageToXml(new SendMessageVoice(toUserName, fromUserName, createTime, voice));
			logger.debug("返回的消息："+result);
			return result;
		//回复视频消息
		case 3:
			
			Video video = new Video("txuR4p9wczA-bGvEkmU7lVO4eyxSLkGUseHQBBq9UfQ", "我是视频标题", "我是视频描述");
			result = MessageUtil.videoMessageToXml(new SendMessageVideo(toUserName, fromUserName, createTime, video));
			logger.debug("返回的消息："+result);
			return result;
		//回复音乐消息
		case 4:
			
			String url = "http://1v6721s159.iask.in/yd-service/media/Chains.mp3";
			
			Music music = new Music("我是音乐标题", "我是音乐描述", url, url, "txuR4p9wczA-bGvEkmU7lUOrx1zlOQ8FUpj2YSi8yyY");
			result = MessageUtil.musicMessageToXml(new SendMessageMusic(toUserName, fromUserName, createTime, music));
			return result;
		//回复图文消息
		case 5:
			Item item1 = new Item("https://www.baidu.com/", "http://mmbiz.qpic.cn/mmbiz_jpg/dyCj6wiceMoTQOpSx7YicV4gHHxUu2A7cib7kqicyaMnicbuCtTc5Mr56WVjbSibsNQHobzERKTJ8Q0NIQeEGNl3vCCg/0?wx_fmt=jpeg", "我是图文标题1", "我是图文描述1");
			Item item2 = new Item("https://www.baidu.com/", "http://mmbiz.qpic.cn/mmbiz_jpg/dyCj6wiceMoTQOpSx7YicV4gHHxUu2A7cib7kqicyaMnicbuCtTc5Mr56WVjbSibsNQHobzERKTJ8Q0NIQeEGNl3vCCg/0?wx_fmt=jpeg", "我是图文标题2", "我是图文描述2");
			Item item3 = new Item("https://www.baidu.com/", "http://mmbiz.qpic.cn/mmbiz_jpg/dyCj6wiceMoTQOpSx7YicV4gHHxUu2A7cib7kqicyaMnicbuCtTc5Mr56WVjbSibsNQHobzERKTJ8Q0NIQeEGNl3vCCg/0?wx_fmt=jpeg", "我是图文标题3", "我是图文描述3");
			Item item4 = new Item("https://www.baidu.com/", "http://mmbiz.qpic.cn/mmbiz_jpg/dyCj6wiceMoTQOpSx7YicV4gHHxUu2A7cib7kqicyaMnicbuCtTc5Mr56WVjbSibsNQHobzERKTJ8Q0NIQeEGNl3vCCg/0?wx_fmt=jpeg", "我是图文标题4", "我是图文描述4");
			
			Item[] articles = new Item[]{item1,item2,item3,item4};			
			String articleCount = String.valueOf(articles.length);
			
			result = MessageUtil.newsMessageToXml(new SendMessageNews(toUserName, fromUserName, createTime, articleCount, articles));
			logger.debug("返回的消息："+result);
			return result;
		//位置类型 回复空
		default:
			logger.debug("返回的消息："+result);
			return result;
		}

	}
}
