package com.zhang.util.common;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class DesUtil {

	private static final String KEY = "12345678";

	public static String encode(String input) {
		try {
			byte[] data = input.getBytes("GBK");
			DESKeySpec dks = new DESKeySpec(KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());// 向量
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(data);
			return new BASE64Encoder().encode(bytes).replace("\n", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decode(String input) {
		try {
			byte[] data = new BASE64Decoder().decodeBuffer(input.replace("\n", ""));
			DESKeySpec dks = new DESKeySpec(KEY.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key 的长度不能够小于8 位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(data), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(encode("charles"));
//		System.out.println(encode("中国人"));
//		System.out.println(decode(encode("中国人")));
//		System.out.println(decode("S078JKKtSvAMWap/sIGpd4eiphXoB0MB8EfFuFogsz8J06DWPBdsx2S/eOO8duPUf02TmvGZmmrFkjxBXifwlgsiFvw2v5kxOxwMpI4tGQsNOiTDklWlLUc5Q6+kTAGnFtgjR8SBdT2xhzifYO4aLg=="));
//		System.out.println(decode("6rtTnrF34mPkJ5SO3RiaaQ=="));
//		System.out.println(decode("S078JKKtSvAMWap/sIGpd4eiphXoB0MB8EfFuFogsz8J06DWPBdsx2S/eOO8duPUiPWhgO73An4kFhzz4vMgHZLXNCfoNkJlPJ3t42Vs5F4+JKvxQDz/wE/pQaLP9i1YK6vcsYTH+4w="));
	}

}
