package com.yyb.framework.pigeon.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/20
 * @description
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLog {
    //接口方法上的描述信息
    String desc() default "";
}
