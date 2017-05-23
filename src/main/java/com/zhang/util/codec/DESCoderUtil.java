package com.zhang.util.codec;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class DESCoderUtil {

	private static String DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";
	private static String CODE_VECTOR = "12345678";
	public static String KEY = "12345678";
	public static String DEFAULT_CHARSET_NAME = "GB2312";

    public static String encode(String data) throws Exception {
        return encode(KEY, data.getBytes(DEFAULT_CHARSET_NAME));
    }

    public static String decode(String data) throws Exception {
        return new String(decode(KEY, data.getBytes()), DEFAULT_CHARSET_NAME);
    }

    public static String encode(String key, byte[] data) throws Exception {
        Key secretKey = toKey(key);
        Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(CODE_VECTOR.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        byte[] bytes = cipher.doFinal(data);
        return Base64Util.byteArrayToBase64(bytes);
    }

    public static byte[] decode(String key, byte[] data) throws Exception {
        Key secretKey = toKey(key);
        Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(CODE_VECTOR.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        return cipher.doFinal(Base64.decodeBase64(data));
    }

    private static Key toKey(String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(dks);
    }
}