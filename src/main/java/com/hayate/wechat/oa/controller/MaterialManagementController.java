package com.hayate.wechat.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hayate.wechat.common.base.BaseController;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.oa.service.MaterialManagementService;

@Api(tags="微信公众号-素材管理")
@Controller
@RequestMapping(value = "wechat/oa/materialmanagement")
public class MaterialManagementController extends BaseController{
	
	@Autowired
	private MaterialManagementService materialManagementService;

	@ApiOperation(value = "临时素材-新增") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "multipartFile", value = "媒体文件",required = true, dataType = "File", paramType = "form"),
		@ApiImplicitParam(name = "mediaType", value = "媒体文件类型(-1:略缩图 0:图片 1:语音 2:视频 )",required = true, dataType = "Integer", paramType = "query")
	})
	@RequestMapping(value="upload-temp", method = RequestMethod.POST)
	@ResponseBody
	public String uploadTemp(@RequestParam(required=true)MultipartFile multipartFile,@RequestParam(required=true)int mediaType){
		
		String log = "临时素材-新增";
		logger.info("收到"+log+"请求");	
		String fileName = multipartFile.getOriginalFilename();
		String fileFormat = fileName.substring(fileName.indexOf(".") + 1);
		
		String type = null;
		
		if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_IMAGE){
			type = WeChatOaConfig.MEDIA_TYPE_IMAGE;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_VOICE){
			type = WeChatOaConfig.MEDIA_TYPE_VOICE;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_VIDEO){
			type = WeChatOaConfig.MEDIA_TYPE_VIDEO;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_THUMB){
			type = WeChatOaConfig.MEDIA_TYPE_THUMB;
		}else{
			return "媒体类型错误";
		}
		
		try {
			byte[] data = multipartFile.getBytes();
			return materialManagementService.uploadTemp(type, data, fileFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ApiOperation(value = "临时素材-获取") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "mediaId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="get-temp", method = RequestMethod.GET)
	public void getTemp(@RequestParam(required=true)String mediaId,HttpServletResponse response){
		
		String log = "临时素材-获取";
		logger.info("收到"+log+"请求");	
		byte[] data = materialManagementService.getTemp(mediaId);
		try {
			response.getOutputStream().write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value = "临时素材-获取视频") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "mediaId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="get-temp-video", method = RequestMethod.GET)
	@ResponseBody
	public String getVideoTemp(@RequestParam(required=true)String mediaId){
		
		String log = "临时素材-获取视频";
		logger.info("收到"+log+"请求");
		return materialManagementService.getVideoTemp(mediaId);
	}
	
	@ApiOperation(value = "永久素材-新增(图片、语音、略缩图)") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "multipartFile", value = "媒体文件",required = true, dataType = "File", paramType = "form"),
		@ApiImplicitParam(name = "mediaType", value = "媒体文件类型(-1:略缩图 0:图片 1:语音)",required = true, dataType = "Integer", paramType = "query")
	  })
	@RequestMapping(value="upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadMaterial(@RequestParam(required=true)MultipartFile multipartFile,@RequestParam(required=true)int mediaType){
		
		String log = "永久素材-新增(图片、语音、略缩图)";
		logger.info("收到"+log+"请求");	
		String fileName = multipartFile.getOriginalFilename();
		String fileFormat = fileName.substring(fileName.indexOf(".") + 1);
		
		String type = null;
		
		if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_IMAGE){
			type = WeChatOaConfig.MEDIA_TYPE_IMAGE;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_VOICE){
			type = WeChatOaConfig.MEDIA_TYPE_VOICE;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_THUMB){
			type = WeChatOaConfig.MEDIA_TYPE_THUMB;
		}else{
			return "媒体类型错误";
		}
		try {
			byte[] data = multipartFile.getBytes();
			return materialManagementService.uploadMaterial(type, data, fileFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ApiOperation(value = "永久素材-新增视频") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "multipartFile", value = "媒体文件",required = true, dataType = "File", paramType = "form"),
		@ApiImplicitParam(name = "title", value = "视频素材的标题",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "introduction", value = "视频素材的描述",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="upload-video", method = RequestMethod.POST)
	@ResponseBody
	public String uploadVideoMaterial(@RequestParam(required=true)MultipartFile multipartFile,@RequestParam(required=true)String title,@RequestParam(required=true)String introduction){
		
		String log = "永久素材-新增视频";
		logger.info("收到"+log+"请求");	
		String fileName = multipartFile.getOriginalFilename();
		String fileFormat = fileName.substring(fileName.indexOf(".") + 1);
		String type = WeChatOaConfig.MEDIA_TYPE_VIDEO;
		
		try {
			byte[] data = multipartFile.getBytes();
			return materialManagementService.uploadMaterialEx(type, data, fileFormat, title, introduction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@ApiOperation(value = "永久素材-获取(图片、语音、略缩图)") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "mediaId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="get", method = RequestMethod.GET)
	public void getMaterial(@RequestParam(required=true)String mediaId,HttpServletResponse response){
		
		String log = "永久素材-获取(图片、语音、略缩图)";
		logger.info("收到"+log+"请求");
		byte[] data = materialManagementService.getMaterial(mediaId);
		try {
			response.getOutputStream().write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value = "永久素材-获取视频url") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "mediaId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="get-video", method = RequestMethod.GET)
	@ResponseBody
	public String getVideoMaterial(@RequestParam(required=true)String mediaId){
		
		String log = "永久素材-获取视频url";
		logger.info("收到"+log+"请求");	
		return materialManagementService.getVideoMaterial(mediaId);
	}
	
	@ApiOperation(value = "永久素材-获取图文") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "mediaId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="get-news", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>>  getNewsMaterial(@RequestParam(required=true)String mediaId){
		
		String log = "永久素材-获取图文";
		logger.info("收到"+log+"请求");
		return materialManagementService.getNewsMaterial(mediaId);
	}
	
	@ApiOperation(value = "永久素材-删除") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "mediaId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMaterial(@RequestParam(required=true)String mediaId){
		
		String log = "永久素材-删除";
		logger.info("收到"+log+"请求");
		return materialManagementService.deleteMaterial(mediaId);
	}
	
	@ApiOperation(value = "永久素材-总数") 
	@RequestMapping(value="count", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMaterialCount(){
		
		String log = "永久素材-总数";
		logger.info("收到"+log+"请求");
		return materialManagementService.getMaterialCount();
	}
	
	@ApiOperation(value = "永久素材-列表") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaType", value = "媒体文件类型(0:图片 1:语音 2:视频  3:图文)",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "offset", value = "从全部素材的该偏移位置开始返回，0表示从第一个素材 返回",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "count", value = "返回素材的数量，取值在1到20之间",required = true, dataType = "Integer", paramType = "query")
	})
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getMaterialList(@RequestParam(required=true)int mediaType,@RequestParam(required=true)int offset,@RequestParam(required=true)int count){
		
		String log = "永久素材-列表";
		logger.info("收到"+log+"请求");	
		String type = null;
		
		if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_IMAGE){
			type = WeChatOaConfig.MEDIA_TYPE_IMAGE;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_VOICE){
			type = WeChatOaConfig.MEDIA_TYPE_VOICE;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_VIDEO){
			type = WeChatOaConfig.MEDIA_TYPE_VIDEO;
		}else if(mediaType == WeChatOaConfig.INT_MEDIA_TYPE_NEWS){
			type = WeChatOaConfig.MEDIA_TYPE_NEWS;
		}else{
			return null;
		}
		return materialManagementService.getMaterialList(type, offset, count);
	}
	
	@ApiOperation(value = "永久素材-上传图文中的图片") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "multipartFile", value = "图片文件",required = true, dataType = "File", paramType = "form")
	})
	@RequestMapping(value="upload-news", method = RequestMethod.POST)
	@ResponseBody
	public String uploadArticlePic(@RequestParam(required=true)MultipartFile multipartFile){
		
		String log = "永久素材-上传图文中的图片";
		logger.info("收到"+log+"请求");	
		String fileName = multipartFile.getOriginalFilename();
		String fileFormat = fileName.substring(fileName.indexOf(".") + 1);
		
		try {
			byte[] data = multipartFile.getBytes();
			return materialManagementService.uploadArticlePic(data,fileFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ApiOperation(value = "永久素材-创建一条图文") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "title", value = "图文消息的标题",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "thumb_media_id", value = "图文消息的封面图片素材id（必须是永久mediaID）",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "author", value = "作者",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "digest", value = "图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "show_cover_pic", value = "是否显示封面，0为false，即不显示，1为true，即显示",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "content", value = "图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源\"上传图文消息内的图片获取URL\"接口获取。外部图片url将被过滤",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "content_source_url", value = "图文消息的原文地址，即点击“阅读原文”后的URL",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="create-article", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createNews(@RequestParam(required=true)String title,@RequestParam(required=true)String thumb_media_id,@RequestParam(required=true)String author,@RequestParam(required=true)String digest,
			@RequestParam(required=true)int show_cover_pic,@RequestParam(required=true)String content,@RequestParam(required=true)String content_source_url){
		
		String log = "永久素材-创建一条图文";
		logger.info("收到"+log+"请求");
		return materialManagementService.createNews(title, thumb_media_id, author, digest, show_cover_pic, content, content_source_url);
	}
	
	@ApiOperation(value = "永久素材-创建图文组合") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "articles", value = "图文组合数据（JSON格式）",required = true, dataType = "Model", paramType = "body")
	})
	@RequestMapping(value="create-group", method = RequestMethod.POST)
	@ResponseBody
	public String createNewsGroup(@RequestBody List<Map<String,Object>> articles){
		
		String log = "永久素材-创建图文组合";
		logger.info("收到"+log+"请求");
		return materialManagementService.createNewsGroup(articles);
	}
	
	@ApiOperation(value = "永久素材-修改图文组合中的一条") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mediaId", value = "要修改的图文消息的id",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "index", value = "要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "articles", value = "图文内容",required = true, dataType = "Model", paramType = "body")
	})
	@RequestMapping(value="update-group", method = RequestMethod.POST)
	@ResponseBody
	public String updateNewsGroup(@RequestParam(required=true) String mediaId,@RequestParam(required=true) int index,@RequestBody Map<String,Object> articles){
		
		String log = "永久素材-修改图文组合中的一条";
		logger.info("收到"+log+"请求");	
		return materialManagementService.updateNewsGroup(mediaId, index, articles);
	}
}
