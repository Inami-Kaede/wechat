package com.hayate.wechat.common.util;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetUtil {

	private static String LocalHostName = null;

	private static String LocalIp = null;

	private static Logger log = LoggerFactory.getLogger(NetUtil.class);

	public static String getLocalHostName() {
		if (StringUtils.isBlank(LocalHostName)) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				LocalHostName = addr.getHostName();
			} catch (Exception e) {
				log.error("getLocalHostName error", e);
				return "";
			}
		}
		return LocalHostName;
	}

	public static String getLocalIp() {
		if (StringUtils.isBlank(LocalIp)) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				LocalIp = addr.getHostAddress();
			} catch (Exception e) {
				log.error("LocalIp error", e);
				return "";
			}
		}
		return LocalIp;
	}

	public static void main(String[] args) {
		System.out.println(NetUtil.getLocalIp());
	}

}
