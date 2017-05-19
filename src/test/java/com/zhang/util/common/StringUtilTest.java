package com.zhang.util.common;

import org.junit.Test;

/**
 * Created by zhangsl on 2017/3/9.
 */
public class StringUtilTest {
    String first = null;
    String second = "null";
    String third = "";
    String forth = "         ";
    String fifth = "test";

    @Test
    public void testIsEmpty() {
        System.out.println("Testing isEmpty");
        if (!StringUtil.isEmpty(first)) {
            System.err.println("空对象不通过");
        } else if (!StringUtil.isEmpty(second)) {
            System.err.println("null字符串不通过");
        } else if (!StringUtil.isEmpty(third)) {
            System.err.println("空字符串不通过");
        } else if (!StringUtil.isEmpty(forth)) {
            System.err.println("空白不通过");
        } else if (StringUtil.isEmpty(fifth)) {
            System.err.println("非空白不通过");
        } else {
            System.out.println("Pass");
        }
    }

    @Test
    public void testIsNotEmpty() {
        System.out.println("Testing isNotEmpty");
        if (StringUtil.isNotEmpty(first)) {
            System.err.println("空对象不通过");
        } else if (StringUtil.isNotEmpty(second)) {
            System.err.println("null字符串不通过");
        } else if (StringUtil.isNotEmpty(third)) {
            System.err.println("空字符串不通过");
        } else if (StringUtil.isNotEmpty(forth)) {
            System.err.println("空白不通过");
        } else if (!StringUtil.isNotEmpty(fifth)) {
            System.err.println("非空白不通过");
        } else {
            System.out.println("Pass");
        }
    }

    @Test
    public void testToInteger() {
        System.out.println("Testing toInteger...");
        String one = "1";
        if (StringUtil.toInteger(first) != 0) {
            System.err.println("不通过");
        } else if (StringUtil.toInteger(one) != 1) {
            System.err.println("不通过");
        } else {
            System.out.println("Pass");
        }
    }

    @Test
    public void testToString() {
        System.out.println("Testing toString...");
        if (StringUtil.toString(first) != first) {
            System.out.println("不通过");
        } else if (StringUtil.toString(second) != null) {
            System.out.println("不通过");
        } else {
            System.out.println("Pass");
        }
    }


}
