package com.zhang.util.common;

import com.zhang.util.base.BaseUtil;

import java.text.DecimalFormat;

/**
 * Created by zhangsl on 2017/3/9.
 */

public class StringUtil extends BaseUtil{


	public static boolean isEmpty(String s) {
		return isNull(s) || s.trim().isEmpty() || s.equalsIgnoreCase("null");
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

    public static Integer toInteger(String s) {
        return isEmpty(s)? 0 : Integer.valueOf(s);
    }
    
	public static String toString(Object obj) {
		return isNull(obj) ? null : obj.toString();
	}

	public static String toStringNotNull(Object obj) {
		return isNull(obj) ? "" : obj.toString();
	}

	public static void main(String[] args) {
		System.out.print(new DecimalFormat("0.00").format(0));
	}

	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}
	
	
	
	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
	
	
	
	// 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
	
	
}
