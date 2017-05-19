package com.zhang.util;

public class RegularUtil {

    /** 统一社会信用代码 */
    public static final String REG_CREDIT_CODE = "9[1|2|3]([A-Za-z0-9]{16})";
    /** 组织机构代码 */
    public static final String ORG_CODE = "[0-9A-Za-z]{9}";
    /** 注册号 */
    public static final String REG_NO = "[0-9A-Za-z]{9}";

    public static final String MOBILE = "((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}";
    
    /**
     *  验证正则表达式 
     */
    public static boolean validate(String arg,String regex){
        if (StringUtil.isNotEmpty(arg)){
            return arg.matches(regex);
        }
        return false;
    }
}
