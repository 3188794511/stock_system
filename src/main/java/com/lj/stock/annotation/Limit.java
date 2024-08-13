package com.lj.stock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 表示该注解会在运行时保留，可以通过反射获取
@Target(ElementType.METHOD) // 表示该注解可以应用于方法上
public @interface Limit {
    long limitTime() default 10;
}
