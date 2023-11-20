package com.itheima.reggie.common;

/**
 * @description:
 * @author: ChenZhao
 * @Date 2023/11/10 17:32
 * @since 1.0.0
 * TODO:自定义业务异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
