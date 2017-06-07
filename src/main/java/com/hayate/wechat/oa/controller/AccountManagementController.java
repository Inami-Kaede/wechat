package com.hayate.wechat.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hayate.wechat.oa.service.AccountManagementService;

@Api(tags="微信公众号-账号管理")
@Controller
@RequestMapping(value = "wechat/oa/accountmanagement")
public class AccountManagementController extends BaseController {
	
	@Autowired
	private AccountManagementService accountManagementService;
	
	@ApiOperation(value = "创建带参二维码") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户openId",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "type", value = "二维码类型:0 临时消息 1永久消息 2永久消息（消息内容为String类型）",required = true, dataType = "Integer", paramType = "query")
	  })
	@RequestMapping(value="qrcode", method = RequestMethod.POST)
	@ResponseBody
	public String createParamQrcode(@RequestParam(required=true)String userId,@RequestParam(required=true)int type){
		
		String log = "创建带参二维码";
		logger.info("收到"+log+"请求");	
		
		return accountManagementService.createParamQrcode(userId, type);
	}
	
	@ApiOperation(value = "创建短链接") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "url", value = "原链接",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="short-url", method = RequestMethod.POST)
	@ResponseBody
	public String getShortUrl(@RequestParam(required=true) String url){
		
		String log = "创建短链接";
		logger.info("收到"+log+"请求");		
		return accountManagementService.getShortUrl(url);
	}
}
