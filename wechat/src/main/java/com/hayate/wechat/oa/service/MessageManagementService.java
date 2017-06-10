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

}
