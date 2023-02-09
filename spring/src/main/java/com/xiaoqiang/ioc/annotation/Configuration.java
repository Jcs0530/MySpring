package com.xiaoqiang.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoqiang
 * @date 2019/10/6-20:02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    String value() default "";
}
