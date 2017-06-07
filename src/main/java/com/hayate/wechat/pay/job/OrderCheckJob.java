package com.hayate.wechat.pay.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Lazy(false)
@Transactional(readOnly = true)
public class OrderCheckJob {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 每天定时检查数据库所有订单状态为未支付的订单，
	 * 如果查询到状态为未付款则关闭订单，
	 * 如果查询到状态为已支付则做出相应处理
	 */
	
	//每一分钟（测试用）
	//@Scheduled(cron = "0 0/1 * * * ?")
	//每天十二点
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional(readOnly = false)
	public void OrderCheck(){
		
		logger.info("★★★★★★★★★★★★★★例行订单检查（开始）★★★★★★★★★★★★★★");
		
		//时间处理:微信通知时间最长为三小时四分钟，考虑到接收时长和程序处理时间应对检查订单的时间做处理
		//获取当前时间
		Date today = new Date();
		DateTime dt = new DateTime(today);
		
		//当前时间减去4小时
		DateTime before1 = dt.minusHours(4);
		
		//在上面基础上减去一天
		DateTime before2 = before1.minusDays(1);
		
		//在上面基础上再减去一分钟（防止程序执行时间的误差）
		DateTime before3 = before2.minusMinutes(1);
		
		logger.info("===============================================================");
		logger.info("开始检查从："+before3.toString("yyyy-MM-dd HH:mm:ss")+"到"+before1.toString("yyyy-MM-dd HH:mm:ss")+"的未支付订单记录！");
		logger.info("===============================================================");
		
		logger.info("★★★★★★★★★★★★★★例行订单检查（结束）★★★★★★★★★★★★★★");
	
	}
		
}
