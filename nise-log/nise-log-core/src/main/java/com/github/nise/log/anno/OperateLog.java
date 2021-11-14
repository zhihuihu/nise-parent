package com.github.nise.log.anno;

import java.lang.annotation.*;

/**
 * 日志操作注解
 * @author huzhihui
 * @version $ v 0.1 2021/11/10 8:01 huzhihui Exp $$
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {

    /**
     * 模块
     * @return
     */
    String module() default "";

    /**
     * 名称
     * @return
     */
    String name() default "";
}
