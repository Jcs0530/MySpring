package com.xiaoqiang.mvc.annotation;

import com.xiaoqiang.mvc.bean.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaoqiang
 * @date 2019/10/7-13:59
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value();

    RequestMethod method() default RequestMethod.GET;

}
