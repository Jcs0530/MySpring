package com.xiaoqiang.aop.advice;

import com.xiaoqiang.ioc.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xiaoqiang
 * @date 2019/10/9-15:21
 */
public interface Advice {

    String getAdviceType();

    void after(Object[] args, Object target, Object returnVal,Method method);

    Object around(Method method, Object[] args, Object target) throws InvocationTargetException, IllegalAccessException;

    void before(Object[] args, Object target,Method method);

}
