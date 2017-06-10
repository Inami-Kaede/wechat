package com.hayate.wechat.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hayate.wechat.common.base.BaseController;
import com.hayate.wechat.oa.service.DataCubeService;

@Api(tags="微信公众号-数据统计模块")
@Controller
@RequestMapping(value = "wechat/oa/datacube")
public class DataCubeController extends BaseController {
	
	@Autowired
	private DataCubeService dataCubeService;
	
	@ApiOperation(value = "获取用户增减数据",notes="时间跨度：7") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="usersummary", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUserSummary(Date begin,Date end){
		String log = "获取用户增减数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUserSummary(begin, end);
	}
	
	@ApiOperation(value = "获取累计用户数据",notes="时间跨度：7") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="usercumulate", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUserCumulate(Date begin,Date end){
		String log = "获取累计用户数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUserCumulate(begin, end);
	}
	
	@ApiOperation(value = "获取图文群发每日数据",notes="时间跨度：1") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="articlesummary", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getArticleSummary(Date begin,Date end){
		String log = "获取图文群发每日数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getArticleSummary(begin, end);
	}
	
	@ApiOperation(value = "获取图文群发总数据",notes="时间跨度：1") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="articletotal", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getArticleTotal(Date begin,Date end){
		String log = "获取图文群发总数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getArticleTotal(begin, end);
	}
	
	@ApiOperation(value = "获取图文统计数据",notes="时间跨度：3") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="userread", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUserRead(Date begin,Date end){
		String log = "获取图文统计数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUserRead(begin, end);
	}

	@ApiOperation(value = "获取图文统计分时数据",notes="时间跨度：1") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="userreadhour", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUserReadHour(Date begin,Date end){
		String log = "获取图文统计分时数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUserReadHour(begin, end);
	}
	
	@ApiOperation(value = "获取图文分享转发数据",notes="时间跨度：7") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="usershare", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUserShare(Date begin,Date end){
		String log = "获取图文分享转发数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUserShare(begin, end);
	}
	
	@ApiOperation(value = "获取图文分享转发分时数据",notes="时间跨度：1") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="usersharehour", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUserShareHour(Date begin,Date end){
		String log = "获取图文分享转发分时数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUserShareHour(begin, end);
	}
	
	@ApiOperation(value = "获取消息发送概况数据",notes="时间跨度：7") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="upstreammsg", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUpStreamMsg(Date begin,Date end){
		String log = "获取消息发送概况数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUpStreamMsg(begin, end);
	}
	
	@ApiOperation(value = "获取消息分送分时数据",notes="时间跨度：1") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="upstreammsghour", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUpStreamMsgHour(Date begin,Date end){
		String log = "获取消息分送分时数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUpStreamMsgHour(begin, end);
	}
	
	@ApiOperation(value = "获取消息发送周数据",notes="时间跨度：30") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="upstreammsgweek", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUpStreamMsgWeek(Date begin,Date end){
		String log = "获取消息发送周数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUpStreamMsgWeek(begin, end);
	}
	
	@ApiOperation(value = "获取消息发送月数据",notes="时间跨度：30") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="upstreammsgmonth", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUpStreamMsgMonth(Date begin,Date end){
		String log = "获取消息发送月数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUpStreamMsgMonth(begin, end);
	}
	
	@ApiOperation(value = "获取消息发送分布数据",notes="时间跨度：15") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="upstreammsgdist", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUpStreamMsgDist(Date begin,Date end){
		String log = "获取消息发送分布数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUpStreamMsgDist(begin, end);
	}
	
	@ApiOperation(value = "获取消息发送分布周数据",notes="时间跨度：30") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="upstreammsgdistweek", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUpStreamMsgDistWeek(Date begin,Date end){
		String log = "获取消息发送分布周数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUpStreamMsgDistWeek(begin, end);
	}
	
	@ApiOperation(value = "获取消息发送分布月数据",notes="时间跨度：30") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="upstreammsgdistmonth", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUpStreamMsgDistMonth(Date begin,Date end){
		String log = "获取消息发送分布月数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getUpStreamMsgDistMonth(begin, end);
	}
	
	@ApiOperation(value = "获取接口分析数据",notes="时间跨度：30") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="interfacesummary", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getInterfaceSummary(Date begin,Date end){
		String log = "获取接口分析数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getInterfaceSummary(begin, end);
	}
	
	@ApiOperation(value = "获取接口分析分时数据",notes="时间跨度：1") 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "begin", value = "开始时间",required = true, dataType = "Date", paramType = "query"),
		@ApiImplicitParam(name = "end", value = "截止时间",required = true, dataType = "Date", paramType = "query")
	  })
	@RequestMapping(value="interfacesummaryhour", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getInterfaceSummaryHour(Date begin,Date end){
		String log = "获取接口分析分时数据";
		logger.info("收到"+log+"请求");	
		return dataCubeService.getInterfaceSummaryHour(begin, end);
	}

}
