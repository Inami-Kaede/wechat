package com.hayate.wechat.oa.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DataCubeService {

	List<Map<String, Object>> getUserSummary(Date begin, Date end);

	List<Map<String, Object>> getUserCumulate(Date begin, Date end);

	List<Map<String, Object>> getArticleSummary(Date begin, Date end);

	List<Map<String, Object>> getArticleTotal(Date begin, Date end);

	List<Map<String, Object>> getUserRead(Date begin, Date end);

	List<Map<String, Object>> getUserReadHour(Date begin, Date end);

	List<Map<String, Object>> getUserShare(Date begin, Date end);

	List<Map<String, Object>> getUserShareHour(Date begin, Date end);

	List<Map<String, Object>> getUpStreamMsg(Date begin, Date end);

	List<Map<String, Object>> getUpStreamMsgHour(Date begin, Date end);

	List<Map<String, Object>> getUpStreamMsgWeek(Date begin, Date end);

	List<Map<String, Object>> getUpStreamMsgMonth(Date begin, Date end);

	List<Map<String, Object>> getUpStreamMsgDist(Date begin, Date end);

	List<Map<String, Object>> getUpStreamMsgDistWeek(Date begin, Date end);

	List<Map<String, Object>> getUpStreamMsgDistMonth(Date begin, Date end);

	List<Map<String, Object>> getInterfaceSummary(Date begin, Date end);

	List<Map<String, Object>> getInterfaceSummaryHour(Date begin, Date end);

}
