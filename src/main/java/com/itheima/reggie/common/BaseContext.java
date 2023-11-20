package com.itheima.reggie.common;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/9 19:40
 * @since 1.0.0
 * TODO:基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
