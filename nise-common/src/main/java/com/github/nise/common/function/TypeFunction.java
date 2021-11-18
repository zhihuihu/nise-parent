/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2021 All Rights Reserved.
 */
package com.github.nise.common.function;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author huzhi
 * @version $ v 0.1 2021/11/18 20:40 huzhi Exp $$
 */
@FunctionalInterface
public interface TypeFunction<T,R> extends Serializable, Function<T,R> {

    /**
     * 获取列名称
     * @param lambda
     * @return String
     */
    static String getLambdaColumnName(Serializable lambda) {
        try {
            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            String getter = serializedLambda.getImplMethodName();
            String fieldName = null;
            if(getter.startsWith("get")){
                fieldName = Introspector.decapitalize(getter.substring(3));

            }else if(getter.startsWith("is")){
                fieldName = Introspector.decapitalize(getter.substring(2));
            }
            if(null == fieldName){
                throw new RuntimeException("get方法错误");
            }
            return fieldName;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
