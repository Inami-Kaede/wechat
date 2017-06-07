package com.hayate.wechat.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hayate.wechat.oa.service.AuthService;

@Api(tags="微信公众号-网页授权")
@Controller
@RequestMapping(value = "wechat/oa/auth")
public class AuthController extends BaseController {
	
	@Autowired
	private AuthService authService;

	@ApiOperation(value = "微信登录（公众号范围）-用户授权跳转")
	@RequestMapping(value="redirect", method = RequestMethod.GET)
	public void authRedirect(HttpServletResponse response){		
		
		String log = "微信登录（公众号范围）-用户授权跳转";
		logger.info("收到"+log+"请求");	
		String loginUrl = authService.getAuthUrl("", 1);
		logger.debug("生成的微信授权跳转链接："+loginUrl);
		try {
			response.sendRedirect(loginUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ApiOperation(value = "微信登录（公众号范围）-用户授权后回调", notes = "通过获取的code换取用户信息") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "用户同意授权后获取的code",required = true, dataType = "String", paramType = "query")		
	  })
	@RequestMapping(value="userinfo", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public Map<String, Object> getUserInfoByCode(@RequestParam(required=true)String code){
		
		String log = "微信登录（公众号范围）-用户授权后回调";
		logger.info("收到"+log+"请求");
		return authService.getUserInfoByCode(code);
	}
}
