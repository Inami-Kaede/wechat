package com.hayate.wechat.oa.service;

public interface AccountManagementService {

	String createParamQrcode(String userId, int type);

	String getShortUrl(String url);

}
