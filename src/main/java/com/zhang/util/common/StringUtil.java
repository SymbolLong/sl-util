package com.zhang.util.common;

import com.zhang.util.base.BaseUtil;

/**
 * Created by zhangsl on 2017/3/9.
 */

public class StringUtil extends BaseUtil {


    public static boolean isEmpty(String s) {
        return isNull(s) || s.trim().isEmpty() || s.equalsIgnoreCase("null");
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static Integer toInteger(String s) {
        return isEmpty(s) ? 0 : Integer.valueOf(s);
    }

    public static String toString(Object obj) {
        return isNull(obj) || isEmpty(obj.toString()) ? null : obj.toString();
    }

    public static String toStringNotNull(Object obj) {
        return isNull(obj) || isEmpty(obj.toString()) ? "" : obj.toString();
    }

    /**
     * String 转 Unicode
     *
     * @param string
     * @return
     */
    public static String string2Unicode(String string) {
        string = toStringNotNull(string);
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);// 取出每一个字符
            unicode.append("\\u" + Integer.toHexString(c));// 转换为unicode
        }
        return unicode.toString();
    }

    /**
     * Unicode 转 String
     *
     * @param unicode
     * @return
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);// 转换出每一个代码点
            string.append((char) data);// 加成string
        }
        return string.toString();
    }


    /**
     * 判断中文汉字和符号
     *
     * @param strName
     * @return
     */
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


    /**
     * 根据Unicode编码判断中文汉字和符号
     *
     * @param c
     * @return
     */
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
