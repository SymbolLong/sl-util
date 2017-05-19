package com.zhang.util;

import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yscredit.util.codec.MD5;

public class GsQueryUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(GsQueryUtil.class);
	private static final HttpUtil HTTP_UTIL = new HttpUtil();
	private static final int READ_TIMEOUT = 45000;
	private static final int CONNECT_TIMEOUT = 2000;
	
	public static String execute(String url, String api, String uid, String key, Map<String, Object> argsMap,
			int connectTimeout, int readTimeout) {
		String sign = "", res = "", args = "", gsUrl = null;
		try {
			args = URLEncoder.encode(JsonUtil.toJsonString(argsMap), "utf-8");
			sign = MD5.sign("uid=" + uid + "&api=" + api + "&args=" + JsonUtil.toJsonString(argsMap) + "&key=" + key);
			gsUrl = url + "?uid=" + uid + "&api=" + api + "&args=" + args + "&sign=" + sign;
			res = HTTP_UTIL.doGet(gsUrl, connectTimeout, readTimeout);
		} catch (Exception e) {
			LOGGER.error("gsUrl=" + gsUrl, e);
		}
		return res;
	}
	
	public static String execute(String url, String api, String uid, String key,
			Map<String, Object> argsMap) {
		return execute(url, api, uid, key, argsMap, CONNECT_TIMEOUT, READ_TIMEOUT);
	}

}
