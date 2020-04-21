package com.sjw.test.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/21 16:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    /**
     * 所属模块
     * @return
     */
    String module() default "";

    /**
     * 内容
     * @return
     */
    String content() default "";

    /**
     * 动作
     * @return
     */
    String action() default "";
}
