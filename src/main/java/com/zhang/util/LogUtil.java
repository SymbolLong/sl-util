package com.zhang.util;

public class LogUtil {
	public final static String THIRD_PARTY_LOG_FMT = "third party interface info: channel name: %s, time cost: %d, return code: %s, third party response code: %s, status: %s";
	
	public static final String format(String channelName, long timeCost, String returnCode, String resCode, String status){
		return String.format(THIRD_PARTY_LOG_FMT, channelName, timeCost, returnCode, resCode == null ? "失败" : resCode,
				status == null ? "失败" : status);
	}
}
