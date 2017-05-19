package com.zhang.util.codec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSA {

	private static Cipher cipher;

	static {
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成密钥对
	 * 
	 * @param filePath
	 *            生成密钥的路径
	 * @return
	 */
	public static Map<String, String> generateKeyPair(String filePath) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 密钥位数
			keyPairGen.initialize(1024);
			// 密钥对
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
			PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到公钥字符串
			String publicKeyString = getKeyString(publicKey);
			// 得到私钥字符串
			String privateKeyString = getKeyString(privateKey);
			// 将密钥对写入到文件
			FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
			FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
			BufferedWriter pubbw = new BufferedWriter(pubfw);
			BufferedWriter pribw = new BufferedWriter(prifw);
			pubbw.write(publicKeyString);
			pribw.write(privateKeyString);
			pubbw.flush();
			pubbw.close();
			pubfw.close();
			pribw.flush();
			pribw.close();
			prifw.close();
			// 将生成的密钥对返回
			Map<String, String> map = new HashMap<String, String>();
			map.put("publicKey", publicKeyString);
			map.put("privateKey", privateKeyString);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = (new BASE64Encoder()).encode(keyBytes);
		return s;
	}

	/**
	 * 使用公钥对明文进行加密，返回BASE64编码的字符串
	 * 
	 * @param publicKey
	 * @param plainText
	 * @return
	 */
	public static String encrypt(PublicKey publicKey, String plainText) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] enBytes = cipher.doFinal(plainText.getBytes());
			return (new BASE64Encoder()).encode(enBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用keystore对明文进行加密
	 * 
	 * @param publicKeystore
	 *            公钥文件路径
	 * @param plainText
	 *            明文
	 * @return
	 */
	public static String encrypt(String publicKeystore, String plainText) {
		try {
			FileReader fr = new FileReader(publicKeystore);
			BufferedReader br = new BufferedReader(fr);
			String publicKeyString = "";
			String str;
			while ((str = br.readLine()) != null) {
				publicKeyString += str;
			}
			br.close();
			fr.close();
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString));
			byte[] enBytes = cipher.doFinal(plainText.getBytes());
			return (new BASE64Encoder()).encode(enBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用私钥对明文密文进行解密
	 * 
	 * @param privateKey
	 * @param enStr
	 * @return
	 */
	public static String decrypt(PrivateKey privateKey, String enStr) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
			return new String(deBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用keystore对密文进行解密
	 * 
	 * @param privateKeystore
	 *            私钥路径
	 * @param enStr
	 *            密文
	 * @return
	 */
	public static String decrypt(String privateKeystore, String enStr) {
		try {
			FileReader fr = new FileReader(privateKeystore);
			BufferedReader br = new BufferedReader(fr);
			String privateKeyString = "";
			String str;
			while ((str = br.readLine()) != null) {
				privateKeyString += str;
			}
			br.close();
			fr.close();
			cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyString));
			byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
			return new String(deBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			PrivateKey key = getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMShJas89aWsYlmZmxSENhg8011Y4pFR+vGYcQ4kTi4TOit5EnSckTIAnxQ9obCcjviKT0ILxcCHl//hu6tYxxpPM63EfMuFEJeXyDikdiun4u1uTDLMDO+VLpsI9alvOz5utxcMZoSCZ82113F8BImXNHVd5JUVq10NJKGoQuypAgMBAAECgYAG3uKkEyM6QMXG/7kNhHkna6ivEFYKYeT04DDDH72jmoRj1H0yme3w41irqmOl+jJ8uMiANE0apyJGjKq0GAwM7YxXWCoZ0ouYGD6NPg6UK0CrpcideJlJRCdcYF4RV53nVRed/ZcjAJyg1UC0QJzEtqtsRPB4Aao1pl2huOMKAQJBAPYrjTcLXX5n6GxibdfHAAbRlMoTztc8b7XDg5i7Gx5i8dzsEc+5HHZso349hl8fq8jvpaXXE/0z15tPIHrzqgkCQQDMeyvmCwgAPYR70H9igN0r0U0wJwjSvtoJ1B2BZRGXzeiEfuHGuWndEgg/B/m41tsvgtylabzG1fLvV9elEFWhAkEAiadK3Pfe/PtPraPQtnNR4X46jFFNPXBPKz28DaC7Bl/fzWC4iQaDEvmm5sWyqOiF3+OaJkVBqTnfG7rAd+BfOQJAPrXrsaJicshrEBnSZ2BD9EM9W+4su41Ci08dOWjIq3i/vZq4HV0RxXXZR5Nnl7N1By4oAybZeVV2KscS4JGQ4QJAEtL3jpWkK4oNj+zri1Q5eYU96tp0A4MzTSm2XGmYuMxlFD6HjoviF0DXDKMfWVQbWdhjKIqyUc+fT+POqv+PBw==");

			System.out
					.println(decrypt(
							key,
							"P/CC7/9ernt+mjaPqyATm7tWiGUGNqbQhrKuU7IOV4f4ijgyq+NCxu/LSSr/HZeUwsoR1iE+z4sYDe5Hehsdhyo9lOFlWIxWp1/gTJEnjIqdLsfqjDPzusVocIk0mFJRQFS6EJ5guV2gRI/+80Pdh0855lL2Ugx1E8rvBJ1Qkdg="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
