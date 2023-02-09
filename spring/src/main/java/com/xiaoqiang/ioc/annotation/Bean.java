package com.xiaoqiang.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoqiang
 * @date 2019/10/6-16:45
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    public String value() default "";
}

