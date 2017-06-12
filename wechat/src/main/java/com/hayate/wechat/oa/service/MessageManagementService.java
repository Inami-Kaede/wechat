package com.hayate.wechat.oa.service;

import java.util.List;
import java.util.Map;

public interface MessageManagementService {

	Map<String, Object> pushMessageByTagOrPushAll(String mediaId,
			boolean pushAll, int tagId, String msgType, int sendIgnoreReprint);

	Map<String, Object> pushMessageByOpenIds(String mediaId,
			List<String> openIds, String msgType, int sendIgnoreReprint);

	Map<String, Object> pushMessagePreview(int toUserType, String toUser,
			String mediaId, String msgType);

	Map<String, Object> setIndustry(int industryId1, int industryId2);

	Map<String, Object> getIndustry();

	Map<String, Object> addTemplate(String templateIdShort);

	List<Map<String, Object>> getAllPrivateTemplate();

	Map<String, Object> delPrivateTemplate(String templateId);

	Map<String, Object> send(String touser, String templateId, String url,
			Map<String, Object> miniprogram, Map<String, Object> data);

}
