package com.cs.aop.advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public interface Advice {

    String getAdviceType();

    void after(Object[] args, Object target, Object returnVal,Method method);

    Object around(Method method, Object[] args, Object target) throws InvocationTargetException, IllegalAccessException;

    void before(Object[] args, Object target,Method method);

}
