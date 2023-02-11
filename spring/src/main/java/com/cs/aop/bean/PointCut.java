package com.cs.aop.bean;

import java.lang.reflect.Method;

public interface PointCut {

    boolean matchsClass(Class<?> targetClass, String expression) throws Exception;

    boolean matchsMethod(Class<?> targetClass, Method method, String expression) throws Exception;

}

