package com.hayate.wechat.oa.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.oa.config.WeChatOaConfig;
import com.hayate.wechat.oa.service.MaterialManagementService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class MaterialManagementServiceImpl extends BaseService implements
		MaterialManagementService {

	/**
	 * 上传临时媒体文件：三天有效期
	 * @param type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
	 * @param data
	 * @param fileFormat 文件后缀名
	 * @return
	 */
	@Override
	public String uploadTemp(String type,byte[] data,String fileFormat){
		
		String log = "临时文件上传";
		      	
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		params.put("type", type);
		
		String stringResult = HttpClientUtil.uploadFile(WeChatOaConfig.MEDIA_UPLOAD,params, "media", data,fileFormat);
        
        Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
        
        logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());
        }
        logger.debug(log+"返回信息（结束）");
        if(type.equals(WeChatOaConfig.MEDIA_TYPE_THUMB)){
        	return (String) mapResult.get("thumb_media_id");
        }
        
        return (String) mapResult.get("media_id");
	
	}
	
	/** 
	 * 获取临时素材
	 */
	@Override
	public byte[] getTemp(String mediaId){
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		params.put("media_id", mediaId);
				
		byte[] bytesResult = HttpClientUtil.doGetReceiveBytes(WeChatOaConfig.MEDIA_GET, params);
		return bytesResult;
	}
	
	/** 
	 * 获取视频临时素材
	 * @return 视频下载地址
	 */
	@Override
	public String getVideoTemp(String mediaId){		
		
		String log = "获取视频临时素材";
		
		byte[] bytesResult = getTemp(mediaId);
		try {
			String stringResult = new String(bytesResult, "UTF-8");
	        Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
	        
	        logger.debug(log+"返回信息（开始）");
	        for(Entry<String,Object> e : mapResult.entrySet()){
	        	logger.debug(e.getKey()+":"+e.getValue());
	        }
	        logger.debug(log+"返回信息（结束）");
	        
	        return (String) mapResult.get("video_url");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 用来上传图片、语音、缩略图
	 * 上传永久媒体文件：如果是图片类型会返回media_id和url的字符串，两者之间用逗号分隔
	 * @param type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
	 * @param data
	 * @param fileFormat 文件后缀名
	 * @return media_id
	 */
	@Override
	public String uploadMaterial(String type, byte[] data, String fileFormat) {	
		
		return uploadMaterialEx(type, data, fileFormat, null, null);
	}

	/**
	 * 可以上传允许的所用类型（上传视频必须用这个）
	 * 上传永久媒体文件：如果是图片类型会返回media_id和url的字符串，两者之间用逗号分隔
	 * @param type	媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
	 * @param data
	 * @param fileFormat 文件后缀名
	 * @param title	当type为video时必填：视频标题
	 * @param introduction 当type为video时必填：视频描述
	 * 在上传视频素材时需要POST另一个表单，id为description，包含素材的描述信息，内容格式为JSON，格式如下：{"title":VIDEO_TITLE,"introduction":INTRODUCTION}
	 * @return media_id
	 */
	@Override
	public String uploadMaterialEx(String type,byte[] data,String fileFormat,String title,String introduction){
		      
		String log = "永久文件上传";
		
		String stringResult = null;
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		params.put("type", type);
		
		
		if(type.equals(WeChatOaConfig.MEDIA_TYPE_VIDEO)){			
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("title", title);
			map.put("introduction", introduction);
			String description = JsonUtils.objectToJson(map);
			stringResult = HttpClientUtil.uploadFile(WeChatOaConfig.MATERIAL_ADD_MATERIAL,params, "media", data,fileFormat,description);		
		}else{			
			stringResult = HttpClientUtil.uploadFile(WeChatOaConfig.MATERIAL_ADD_MATERIAL,params, "media", data,fileFormat);		        	
		}
		      
        Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
        
        logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());
        }
        logger.debug(log+"返回信息（结束）");
        
        if(type.equals(WeChatOaConfig.MEDIA_TYPE_IMAGE)){
        	return (String) mapResult.get("media_id")+","+(String) mapResult.get("url");
        }
        return (String) mapResult.get("media_id");
	
	}
	
	/** 
	 * 获取永久素材
	 */
	@Override
	public byte[] getMaterial(String mediaId){
				
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("media_id", mediaId);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		byte[] bytesResult = HttpClientUtil.doPostJsonReceiveBytes(WeChatOaConfig.MATERIAL_GET_MATERIAL, params, json);
		return bytesResult;
	}
	
	/** 
	 * 获取图文永久素材
	 */
	@Override
	public List<Map<String,Object>> getNewsMaterial(String mediaId){
		
		String log = "获取图文素材";
		
		byte[] bytes = getMaterial(mediaId);
		try {
			String stringResult = new String(bytes, "UTF-8");
			logger.debug("返回的json："+stringResult);
			Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
			
			logger.debug(log+"返回信息（开始）");
	        for(Entry<String,Object> e : mapResult.entrySet()){
	        	logger.debug(e.getKey()+":"+e.getValue());       	
	        }
	        logger.debug(log+"返回信息（结束）");
	        
	        List<Map<String,Object>> usersList = (List<Map<String, Object>>) mapResult.get("news_item");
	        
	        logger.debug(log+"返回信息2（开始）");
	        for(Map<String,Object> m : usersList){
	        	for(Entry<String,Object> e : m.entrySet()){
	        		logger.debug(e.getKey()+":"+e.getValue());
	        	}
	        	logger.debug("=================================");
	        }
	        logger.debug(log+"返回信息2（结束）");
	       
	        return usersList;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取视频永久素材
	 */
	@Override
	public String getVideoMaterial(String mediaId){
		
		String log = "获取视频素材";
		
		byte[] bytes = getMaterial(mediaId);
		try {
			String stringResult = new String(bytes, "UTF-8");
			logger.debug("返回的json："+stringResult);
			Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
			
			logger.debug(log+"返回信息（开始）");
	        for(Entry<String,Object> e : mapResult.entrySet()){
	        	logger.debug(e.getKey()+":"+e.getValue());       	
	        }
	        logger.debug(log+"返回信息（结束）");
	        
	        return (String) mapResult.get("down_url");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 删除永久素材
	 * @param mediaId
	 * @return
	 */
	@Override
	public String deleteMaterial(String mediaId){
		
		String log = "删除永久素材";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("media_id", mediaId);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MATERIAL_DEL_MATERIAL, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
		
	}
	
	/**
	 * 获取素材总数
	 * @return
	 */
	@Override
	public Map<String,Object> getMaterialCount(){
		
		String log = "获取素材总数";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.MATERIAL_COUNT, params);
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
	 * 获取素材列表(用来获取图片、视频、语音)
	 * @param type				素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset			从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count				返回素材的数量，取值在1到20之间
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getMaterialList(String type,int offset,int count){
		
		String log = "获取素材列表";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("type", type);
		bodyMap.put("offset", offset);
		bodyMap.put("count", count);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MATERIAL_BATCH_GET_MATERIAL, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        if(((int)mapResult.get("item_count")) > 0){
        	List<Map<String,Object>> list = (List<Map<String, Object>>) mapResult.get("item");
        	
        	logger.debug(log+"返回信息2（开始）");
        	for(Map<String,Object> m : list){
        		logger.debug("=======================================");
        		for(Entry<String,Object> e : m.entrySet()){
        			logger.debug(e.getKey()+":"+e.getValue());    			
        		}
        		
        		if(type.equals(WeChatOaConfig.MEDIA_TYPE_NEWS)){
    				Map<String,Object> content = (Map<String, Object>) m.get("content");
    				List<Map<String,Object>> list2 = (List<Map<String, Object>>) content.get("news_item");
    				
    				for(Map<String,Object> m2 : list2){
    					logger.debug("-------------------------------------------------");
    					for(Entry<String,Object> e2 : m2.entrySet()){
    						logger.debug(e2.getKey()+":"+e2.getValue());
    					}
    					logger.debug("-------------------------------------------------");
    				}
            	}
        		logger.debug("=======================================");
        	}
        	logger.debug(log+"返回信息2（结束）");        	
        	return list;
        }
        return null;
	}

	/**
	 * 上传图文消息中的图片
	 * @param data
	 * @return url
	 */
	@Override
	public String uploadArticlePic(byte[] data,String fileFormat){
		
		String log = "上传图文消息图片";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
	
		String stringResult = HttpClientUtil.uploadFile(WeChatOaConfig.MEDIA_UPLOAD_IMG, params, "media", data, fileFormat);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());
        }
        logger.debug(log+"返回信息（结束）");
		
        return (String) mapResult.get("url");
	}
	
	/**
	 * 新增永久图文素材组
	 * @param json
	 * @return mediaId
	 */
	@Override
	public String createNewsGroup(List<Map<String,Object>> articles){
		
		String log = "新增永久图文素材";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("articles", articles);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MATERIAL_ADD_NEWS, params, json);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());
        }
        logger.debug(log+"返回信息（结束）");
		
        return (String) mapResult.get("media_id");
		
	}
	
	/**
	 * 创建一个图文素材
	 * @param title							标题
	 * @param thumb_media_id				图文消息的封面图片素材id（必须是永久mediaID）
	 * @param author						作者
	 * @param digest						图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
	 * @param show_cover_pic				是否显示封面，0为false，即不显示，1为true，即显示
	 * @param content						图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源"上传图文消息内的图片获取URL"接口获取。外部图片url将被过滤。
	 * @param content_source_url			图文消息的原文地址，即点击“阅读原文”后的URL
	 * @return 
	 */
	@Override
	public Map<String,Object> createNews(String title,String thumb_media_id,String author,String digest,int show_cover_pic,String content,String content_source_url){
		
		Map<String,Object> article = new LinkedHashMap<String, Object>();
		article.put("title", title);
		article.put("thumb_media_id", thumb_media_id);
		article.put("author", author);
		article.put("digest", digest);
		article.put("show_cover_pic", show_cover_pic);
		article.put("content", content);
		article.put("content_source_url", content_source_url);
				
        return article;
		
	}
	
	/**
	 * 修改永久图文素材
	 * @param json
	 * @return mediaId
	 */
	@Override
	public String updateNewsGroup(String mediaId,int index,Map<String,Object> articles){
		
		String log = "修改永久图文素材";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("media_id", mediaId);
		bodyMap.put("index", index);
		bodyMap.put("articles", articles);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.MATERIAL_UPDATE_NEWS, params, json);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());
        }
        logger.debug(log+"返回信息（结束）");
		
        return (String) mapResult.get("errmsg");
		
	}
}
