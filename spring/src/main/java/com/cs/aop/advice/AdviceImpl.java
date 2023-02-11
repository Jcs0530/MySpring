package com.cs.aop.advice;

import com.cs.ioc.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AdviceImpl implements Advice {

    private Method adviceMethod;
    private String adviceType;
    //adviceObject
    private Object adviceObject;

    public AdviceImpl(Method method, String adviceType, Object adviceObject) {
        this.adviceMethod = method;
        this.adviceType = adviceType;
        this.adviceObject = adviceObject;
    }

    public Method getMethod() {
        return adviceMethod;
    }

    public void setMethod(Method method) {
        this.adviceMethod = method;
    }

    @Override
    public String getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(String adviceType) {
        this.adviceType = adviceType;
    }

    @Override
    public void after(Object[] args, Object target, Object returnVal, Method method) {
        returnVal = ReflectionUtils.invoke(adviceMethod, adviceObject);
    }

    @Override
    public Object around(Method method, Object[] args, Object target) throws InvocationTargetException, IllegalAccessException {
        return ReflectionUtils.invoke(adviceMethod, adviceObject);

    }

    @Override
    public void before(Object[] args, Object target, Method method) {
        ReflectionUtils.invoke(adviceMethod, adviceObject);

    }
}
