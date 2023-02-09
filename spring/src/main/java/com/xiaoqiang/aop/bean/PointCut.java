package com.xiaoqiang.aop.bean;

import java.lang.reflect.Method;

/**
 * @author xiaoqiang
 * @date 2019/10/10-14:31
 */
public interface PointCut {

    boolean matchsClass(Class<?> targetClass, String expression) throws Exception;

    boolean matchsMethod(Class<?> targetClass, Method method, String expression) throws Exception;

}

