/**
 * zhihuihu.github.io.
 * Copyright (c) 2016-2022 All Rights Reserved.
 */
package com.github.nise.middle.excel.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author huzhi
 * @version $ v 0.1 2022/2/11 9:49 huzhi Exp $$
 */
public class GenericsUtils {

    /**
     * 获取声明时父类的泛型参数类型第一个
     * @param clazz
     * @return
     */
    public static Class getSuperClassGenericType(Class clazz){
        return getSuperClassGenericType(clazz,0);
    }

    /**
     * 通过反射获取定义Class时声明的父类的泛型参数类型
     * @param clazz
     * @param index
     * @return
     */
    public static Class getSuperClassGenericType(Class clazz,int index){
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)){
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if(index >= params.length || index < 0){
            return Object.class;
        }
        if(!(params[index] instanceof Class)){
            return Object.class;
        }
        return (Class) params[index];
    }
}
