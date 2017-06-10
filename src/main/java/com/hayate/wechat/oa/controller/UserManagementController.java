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

import com.hayate.wechat.common.base.BaseController;
import com.hayate.wechat.oa.service.UserManagementService;

@Api(tags="微信公众号-用户管理")
@Controller
@RequestMapping(value = "wechat/oa/usermanagement")
public class UserManagementController extends BaseController{
	
	@Autowired
	private UserManagementService userManagementService;

	@ApiOperation(value = "标签-创建标签") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagName", value = "标签名",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="tag/create", method = RequestMethod.POST)
	@ResponseBody
	public String creatTag(@RequestParam(required=true) String tagName){
		
		String log = "标签-创建标签";
		logger.info("收到"+log+"请求");
		return userManagementService.creatTag(tagName);
	}
	
	@ApiOperation(value = "标签-修改标签") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagId", value = "标签ID",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "tagName", value = "标签名",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="tag/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateTag(@RequestParam(required=true) int tagId,@RequestParam(required=true) String tagName){
		
		String log = "标签-修改标签";
		logger.info("收到"+log+"请求");	
		return userManagementService.updateTag(tagId, tagName);
	}
	
	@ApiOperation(value = "标签-删除标签") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagId", value = "标签ID",required = true, dataType = "Integer", paramType = "query")
	})
	@RequestMapping(value="tag/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteTag(@RequestParam(required=true) int tagId){
		
		String log = "标签-删除标签";
		logger.info("收到"+log+"请求");
		return userManagementService.deleteTag(tagId);
	}
	
	@ApiOperation(value = "标签-获取已创建标签") 
	@RequestMapping(value="tag/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getTags(){
		
		String log = "标签-获取已创建标签";
		logger.info("收到"+log+"请求");
		return userManagementService.getTags();
	}
	
	@ApiOperation(value = "标签-获取标签下粉丝列表") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagId", value = "标签ID",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "startFrom", value = "第一个拉取的OPENID，不填默认从头开始拉取",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="tag/list-users", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getOpenIdsByTag(@RequestParam(required=true) int tagId,@RequestParam(required=true) String startFrom){
		
		String log = "标签-获取标签下粉丝列表";
		logger.info("收到"+log+"请求");	
		return userManagementService.getOpenIdsByTag(tagId, startFrom);
	}
	
	@ApiOperation(value = "标签-批量为用户打标签") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagId", value = "标签ID",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "openIds", value = "粉丝列表",required = true, dataType = "Model", paramType = "body")
	})
	@RequestMapping(value="tag/tagging-users", method = RequestMethod.POST)
	@ResponseBody
	public String taggingUsers(@RequestParam(required=true) int tagId,@RequestBody List<String> openIds){
		
		String log = "标签-批量为用户打标签";
		logger.info("收到"+log+"请求");
		return userManagementService.taggingUsers(tagId, openIds);
	}
	
	@ApiOperation(value = "标签-批量为用户取消标签") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tagId", value = "标签ID",required = true, dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "openIds", value = "粉丝列表",required = true, dataType = "Model", paramType = "body")
	})
	@RequestMapping(value="tag/untagging-users", method = RequestMethod.POST)
	@ResponseBody
	public String unTaggingUsers(@RequestParam(required=true) int tagId,@RequestBody List<String> openIds){
		
		String log = "标签-批量为用户取消标签";
		logger.info("收到"+log+"请求");	
		return userManagementService.unTaggingUsers(tagId, openIds);
	}
	
	@ApiOperation(value = "标签-获取用户身上的标签列表") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "openId", value = "用户的openId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="tag/user-tags", method = RequestMethod.POST)
	@ResponseBody
	public List<Integer> getUserTags(@RequestParam(required=true) String openId){
		
		String log = "标签-获取用户身上的标签列表";
		logger.info("收到"+log+"请求");
		return userManagementService.getUserTags(openId);
	}
	
	@ApiOperation(value = "用户-用户备注") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "openId", value = "用户的openId",required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "remarkName", value = "备注名",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="user/remark", method = RequestMethod.POST)
	@ResponseBody
	public String userRemark(@RequestParam(required=true) String openId,@RequestParam(required=true) String remarkName){
		
		String log = "用户-用户备注";
		logger.info("收到"+log+"请求");	
		return userManagementService.userRemark(openId, remarkName);
	}
	
	@ApiOperation(value = "用户-获取用户信息") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "openId", value = "用户的openId",required = true, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="user/info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserInfo(@RequestParam(required=true) String openId){
		
		String log = "用户-获取用户信息";
		logger.info("收到"+log+"请求");	
		return userManagementService.getUserInfo(openId);
	}
	
	@ApiOperation(value = "用户-批量获取用户信息") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "openIds", value = "openId集合",required = true, dataType = "Model", paramType = "body")
	})
	@RequestMapping(value="user/infos", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getUsersInfo(@RequestBody List<String> openIds){
		
		String log = "用户-批量获取用户信息";
		logger.info("收到"+log+"请求");
		return userManagementService.getUsersInfo(openIds);
	}
	
	@ApiOperation(value = "用户-获取关注者列表") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startFrom", value = "第一个拉取的OPENID，不填默认从头开始拉取",required = false, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="user/list", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllOpenIds(String startFrom){
		
		String log = "用户-获取关注者列表";
		logger.info("收到"+log+"请求");	
		return userManagementService.getAllOpenIds(startFrom);
	}
	
	@ApiOperation(value = "用户-获取黑名单列表") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startFrom", value = "第一个拉取的OPENID，不填默认从头开始拉取",required = false, dataType = "String", paramType = "query")
	})
	@RequestMapping(value="user/black-list", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getBlackList(String startFrom){
		
		String log = "用户-获取黑名单列表";
		logger.info("收到"+log+"请求");	
		return userManagementService.getBlackList(startFrom);
	}
	
	@ApiOperation(value = "用户-批量拉黑用户") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "openIds", value = "openId集合",required = true, dataType = "Model", paramType = "body")
	})
	@RequestMapping(value="user/blacking", method = RequestMethod.POST)
	@ResponseBody
	public String blackingUsers(@RequestBody List<String> openIds){
		
		String log = "用户-批量拉黑用户";
		logger.info("收到"+log+"请求");	
		return userManagementService.blackingUsers(openIds);
	}
	
	@ApiOperation(value = "用户-批量取消拉黑用户") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "openIds", value = "openId集合",required = true, dataType = "Model", paramType = "body")
	})
	@RequestMapping(value="user/unblacking", method = RequestMethod.POST)
	@ResponseBody
	public String unBlackingUsers(@RequestBody List<String> openIds){
		
		String log = "用户-批量取消拉黑用户";
		logger.info("收到"+log+"请求");	
		return userManagementService.unBlackingUsers(openIds);
	}
}
