package com.cs.aop.advisor;

import com.cs.aop.bean.PointCut;

import java.lang.reflect.Method;

public class RegexMatchAdvisor implements PointCutAdvisor {

    private String adviceName;   // 通知名称
    private String expression;   //
    private PointCut pointCut;
    private Method method;
    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public RegexMatchAdvisor(String adviceName, String expression, PointCut pointCut, Method method) {
        this.adviceName = adviceName;
        this.expression = expression;
        this.pointCut = pointCut;
        this.method = method;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public PointCut getPointCutResolver() {
        return pointCut;
    }

    @Override
    public String getAdviceBeanName() {
        return this.adviceName;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }
}
