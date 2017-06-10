package com.hayate.wechat.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hayate.wechat.common.base.BaseController;

@Api(tags="微信公众号-view按钮跳转")
@Controller
@RequestMapping(value = "wechat/oa/redirect")
public class RedirectController extends BaseController {

	@ApiOperation(value = "业务跳转-商城", httpMethod = "GET")
	@RequestMapping(value="mall", method = RequestMethod.GET)
	public String mallRedirect(){		
		
		String log = "业务跳转-商城";
		logger.info("收到"+log+"请求");
		return "shopping";
	}
}
