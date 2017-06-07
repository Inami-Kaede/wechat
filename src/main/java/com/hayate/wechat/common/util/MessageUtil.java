package com.hayate.wechat.common.util;

import com.thoughtworks.xstream.XStream;
import com.hayate.wechat.oa.pojo.send.SendMessageImage;
import com.hayate.wechat.oa.pojo.send.SendMessageMusic;
import com.hayate.wechat.oa.pojo.send.SendMessageNews;
import com.hayate.wechat.oa.pojo.send.SendMessageText;
import com.hayate.wechat.oa.pojo.send.SendMessageVideo;
import com.hayate.wechat.oa.pojo.send.SendMessageVoice;

public class MessageUtil {
	
	public static String textMessageToXml(SendMessageText text){
		XStream xs = new XStream();
		xs.alias("xml", text.getClass());
		return xs.toXML(text);
	}
	
	public static String imageMessageToXml(SendMessageImage image){
		XStream xs = new XStream();
		xs.alias("xml", image.getClass());
		xs.alias("Image", image.getImage().getClass());
		return xs.toXML(image);
	}
	
	public static String voiceMessageToXml(SendMessageVoice voice){
		XStream xs = new XStream();
		xs.alias("xml", voice.getClass());
		xs.alias("Voice", voice.getVoice().getClass());
		return xs.toXML(voice);
	}
	
	public static String videoMessageToXml(SendMessageVideo video){
		XStream xs = new XStream();
		xs.alias("xml", video.getClass());
		xs.alias("Video", video.getVideo().getClass());
		return xs.toXML(video);
	}
	
	public static String musicMessageToXml(SendMessageMusic music){
		XStream xs = new XStream();
		xs.alias("xml", music.getClass());
		xs.alias("Music", music.getMusic().getClass());
		return xs.toXML(music);
	}
	
	public static String newsMessageToXml(SendMessageNews news){
		XStream xs = new XStream();
		xs.alias("xml", news.getClass());
		xs.alias("item", news.getArticles()[0].getClass());
		return xs.toXML(news);
	}

}
