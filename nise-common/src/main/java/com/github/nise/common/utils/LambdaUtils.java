/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.common.utils;

import com.github.nise.common.function.TypeFunction;

/**
 * Lambda 工具类
 * @author huzhi
 * @version $ v 0.1 2021/11/18 20:54 huzhi Exp $$
 */
public class LambdaUtils {

    /**
     * 获取列明
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> String getColumnName(TypeFunction<T,R> function){
        return TypeFunction.getLambdaColumnName(function);
    }
}
