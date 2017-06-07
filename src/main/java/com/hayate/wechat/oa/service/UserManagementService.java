package com.hayate.wechat.oa.service;

import java.util.List;
import java.util.Map;

public interface UserManagementService {

	String creatTag(String tagName);

	List<Map<String, Object>> getTags();

	String updateTag(int tagId, String tagName);

	String deleteTag(int tagId);

	List<String> getOpenIdsByTag(int tagId, String startFrom);

	String taggingUsers(int tagId, List<String> openIds);

	String unTaggingUsers(int tagId, List<String> openIds);

	List<Integer> getUserTags(String openId);

	String userRemark(String openId, String remarkName);

	Map<String, Object> getUserInfo(String openId);

	List<Map<String, Object>> getUsersInfo(List<String> openIds);

	List<String> getAllOpenIds(String startFrom);

	List<String> getBlackList(String startFrom);

	String blackingUsers(List<String> openIds);

	String unBlackingUsers(List<String> openIds);

}
