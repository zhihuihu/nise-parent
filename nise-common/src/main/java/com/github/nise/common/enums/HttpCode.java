package com.github.nise.common.enums;

import com.github.nise.common.utils.ass.BusinessExceptionAssert;

/**
 * http响应编码
 * @author huzhi
 * @version $ v 0.1 2021/11/15 19:35 huzhi Exp $$
 */
public enum HttpCode implements BusinessExceptionAssert {

    /** 请求成功 */
    SUCCESS(1,"请求成功"),
    /** 服务异常 */
    FAIL(-1,"服务异常"),

    ;

    HttpCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
