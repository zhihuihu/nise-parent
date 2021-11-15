/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.common.utils;

import com.github.nise.common.exception.BusinessException;

/**
 * 断言工具类
 * @author huzhi
 * @version $ v 0.1 2021/11/15 21:26 huzhi Exp $$
 */
public class AssertUtils {

    /**
     * 校验条件是否为真
     * @param exp
     * @param code
     * @param message
     */
    public static void isTrue(boolean exp,Integer code,String message){
        if(!exp){
            throw new BusinessException(code,message);
        }
    }
}
