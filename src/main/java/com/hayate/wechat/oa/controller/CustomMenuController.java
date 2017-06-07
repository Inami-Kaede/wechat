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

import com.hayate.wechat.oa.service.CustomMenuService;

@Api(tags="微信公众号-自定义菜单")
@Controller
@RequestMapping(value = "wechat/oa/custommenu")
public class CustomMenuController extends BaseController{
	
	@Autowired
	private CustomMenuService customMenuService;
	
	@ApiOperation(value = "自定义菜单创建") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "menu", value = "菜单数据(json)",required = true, dataType = "String", paramType = "query")
	  })
	@RequestMapping(value="create", method = RequestMethod.POST)
	@ResponseBody
	public String createMenu(@RequestParam(required=true)String menu){
		
		String log = "自定义菜单创建";
		logger.info("收到"+log+"请求");
		return customMenuService.createMenu(menu);
	}
	
	@ApiOperation(value = "自定义菜单查询") 
	@RequestMapping(value="get", method = RequestMethod.GET)
	@ResponseBody
	public String getMenu(){
		
		String log = "自定义菜单查询";
		logger.info("收到"+log+"请求");	
		return customMenuService.getMenu();
	}
	
	@ApiOperation(value = "自定义菜单删除") 
	@RequestMapping(value="delete", method = RequestMethod.GET)
	@ResponseBody
	public String createMenu(){
		
		String log = "自定义菜单删除";
		logger.info("收到"+log+"请求");
		return customMenuService.deleteMenu();
	}

}
