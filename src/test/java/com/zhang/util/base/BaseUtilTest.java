package com.zhang.util.base;

import org.junit.Test;

/**
 * Created by zhangsl on 2017/3/9.
 *
 */

public class BaseUtilTest {

    @Test
    public void testIsNull(){
        System.out.println("Testing method isNull...");
        Object object = null;
        String string = "";
        if (!BaseUtil.isNull(object)){
            System.err.println("不通过");
        }else if(BaseUtil.isNull(string)){
            System.err.println("不通过");
        }else{
            System.out.println("通过");
        }
    }

    @Test
    public void testIsNotNull(){
        System.out.println("Testing method isNotNull...");
        Object object = null;
        String string = "";
        if (BaseUtil.isNotNull(object)){
            System.err.println("不通过");
        }else if (!BaseUtil.isNotNull(string)){
            System.err.println("不通过");
        }else {
            System.out.println("通过");
        }
    }

}
