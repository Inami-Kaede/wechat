package com.hayate.wechat.oa.service;

import java.util.Map;

public interface AuthService {

	String getAuthUrl(String state, int scope);

	Map<String, Object> getUserInfoByCode(String code);

	Map checkUserAccessToken(String accessToken, String openId);

	Map<String, Object> getUserInfo(String userAccessToken, String openId);

	Map refreshUserAccessToken(String refreshToken);

	Map<String, Object> getUserAccessToken(String code);

}
