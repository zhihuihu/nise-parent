/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package com.github.nise.common.utils;

import com.github.nise.common.enums.HttpCode;
import lombok.Data;

/**
 * 消息响应类
 * @author huzhi
 * @version $ v 0.1 2021/10/23 20:43 huzhi Exp $$
 */
@Data
public class ResponseMessage<T> {

    /** 是否成功 */
    private Boolean success = false;
    /** 消息编码 */
    private Integer code;
    /** 消息信息 */
    private String message;
    /** 消息返回数据 */
    private T data;

    public ResponseMessage() {
    }

    public ResponseMessage(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 请求成功
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(){
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setSuccess(true);
        responseMessage.setCode(HttpCode.SUCCESS.getCode());
        responseMessage.setMessage(HttpCode.SUCCESS.getMessage());
        return responseMessage;
    }

    /**
     * 请求成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(T data){
        ResponseMessage<T> responseMessage = success();
        responseMessage.setData(data);
        return responseMessage;
    }

    /**
     * 请求成功
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(Integer code,String message){
        ResponseMessage<T> responseMessage = success();
        responseMessage.setCode(code);
        responseMessage.setMessage(message);
        return responseMessage;
    }

    /**
     * 请求成功
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(Integer code,String message,T data){
        ResponseMessage<T> responseMessage = success();
        responseMessage.setCode(code);
        responseMessage.setMessage(message);
        responseMessage.setData(data);
        return responseMessage;
    }

    /**
     * 请求失败
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> fail(){
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setSuccess(false);
        responseMessage.setCode(HttpCode.FAIL.getCode());
        responseMessage.setMessage(HttpCode.FAIL.getMessage());
        return responseMessage;
    }

    /**
     * 请求失败
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> fail(String message){
        ResponseMessage<T> responseMessage = fail();
        responseMessage.setMessage(message);
        return responseMessage;
    }

    /**
     * 请求失败
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> fail(Integer code,String message){
        ResponseMessage<T> responseMessage = fail();
        responseMessage.setCode(code);
        responseMessage.setMessage(message);
        return responseMessage;
    }

    public static <T> ResponseMessage<T> fail(Integer code,String message,T data){
        ResponseMessage<T> responseMessage = fail();
        responseMessage.setCode(code);
        responseMessage.setMessage(message);
        responseMessage.setData(data);
        return responseMessage;
    }

}
