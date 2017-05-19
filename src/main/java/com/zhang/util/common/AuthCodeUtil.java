package com.zhang.util.common;

import java.util.Random;

/**
 * @ClassName: AuthCode
 * @Description: 验证码生成器。
 * @version 1.0
 * @date 2014-10-22 下午01:25:43
 */

public final class AuthCodeUtil {

	private static final int AUTH_CODE_MAX = 10;

	private static final int AUTH_CODE_LENGTH = 6;

	private AuthCodeUtil() {

	}

	/**
	 * 生成一个6位的随机验证码，包含当前的时间戳。
	 * 
	 * @param phone
	 *            需要发送验证码的手机号。
	 * @return 验证码。
	 */
	public static String generateAuthCode() {
		Random rd = new Random();
		StringBuilder code = new StringBuilder("");
		int getNum = 0;
		do {
			getNum = Math.abs(rd.nextInt(AUTH_CODE_MAX)); // 产生数字0-9的随机数
			code.append(getNum);
		} while (code.length() < AUTH_CODE_LENGTH);
		return code.toString();
	}
	
	public static  String Code() {
		Random rd = new Random();
		StringBuilder code = new StringBuilder("");
		int getNum = 0;
		do {
			getNum = Math.abs(rd.nextInt(AUTH_CODE_MAX)); // 产生数字0-9的随机数
			code.append(getNum);
		} while (code.length() < 4);
		return code.toString();
	}
}
