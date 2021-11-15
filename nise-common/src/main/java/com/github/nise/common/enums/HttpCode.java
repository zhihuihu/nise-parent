package com.github.nise.common.enums;

/**
 * http响应编码
 * @author huzhi
 * @version $ v 0.1 2021/11/15 19:35 huzhi Exp $$
 */
public enum HttpCode {

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
