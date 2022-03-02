package com.github.nise.common.utils.ass;

import com.github.nise.common.exception.BusinessException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 断言接口
 * @author huzhi
 * @version $ v 0.1 2022/3/2 14:33 huzhi Exp $$
 */
public interface Assert {

    /**
     * 异常
     * @return
     */
    BusinessException businessException();

    /**
     * 断言为真
     * @param expression
     */
    default void isTrue(boolean expression){
        if(!expression){
            throw businessException();
        }
    }

    /**
     * 判断对象不能为null
     * @param data
     */
    default void isNotNull(Object data){
        if(null == data){
            throw businessException();
        }
    }

    /**
     * 判断对象不为空
     * @param data
     */
    default void isNotEmpty(Object data){
        this.isNotNull(data);
        if(data instanceof Optional){
            this.isTrue(((Optional) data).isPresent());
        }else if(data instanceof CharSequence){
            this.isTrue(((CharSequence) data).length() > 0);
        }else if(data.getClass().isArray()){
            this.isTrue(Array.getLength(data) > 0);
        }else if(data instanceof Collection){
            this.isTrue(!((Collection) data).isEmpty());
        }else if(data instanceof Map){
            this.isTrue(!((Map) data).isEmpty());
        }else{
            this.isTrue(false);
        }
    }

    /**
     * 判断字符串不能为空
     * @param data
     */
    default void isNotBlank(String data){
        this.isNotNull(data);
        this.isTrue((data.trim()).length() != 0);
    }
}
