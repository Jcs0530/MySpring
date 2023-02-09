package com.xiaoqiang.aop.postprocessor;

import com.xiaoqiang.aop.advice.Advice;
import com.xiaoqiang.aop.advisor.Advisor;
import com.xiaoqiang.aop.advisor.PointCutAdvisor;
import com.xiaoqiang.aop.advisor.RegexMatchAdvisor;
import com.xiaoqiang.aop.bean.PointCut;
import com.xiaoqiang.aop.factory.AOPFactory;
import com.xiaoqiang.ioc.factory.BeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/10/10-16:18
 */
public class AopProxyCreator implements PointCutAdvisor, PostProcessor {

    //所有的通知
    private List<Advisor> advisors;
    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public AopProxyCreator() {
        advisors = new ArrayList<>();
    }

    @Override
    public PointCut getPointCutResolver() {
        return null;
    }

    @Override
    public String getAdviceBeanName() {
        return null;
    }

    @Override
    public String getExpression() {
        return null;
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Object postProcessWeaving(Object bean, String beanName) throws Exception {
        //获取与当前bean 匹配的 Advisor
        List<Advisor> matchAdvisor = getMatchAdvisor(bean);
        //对bean进行增强 有
        if (!matchAdvisor.isEmpty()) {
            bean = AOPFactory.createProxyInstance().createAopProxyInstance(bean, advisors, beanName).getProxy();
        }
        return bean;
    }

    // 获取所有的通知
    // 通过切点表达式 匹配这个类上所有的通知
    private List<Advisor> getMatchAdvisor(Object bean) throws Exception {
        if (advisors.isEmpty() || bean == null) {
            return null;
        }
        List<Advisor> matchAdvisor = new ArrayList<>();
        Class<?> clazz = bean.getClass();
        for (Advisor advisor : advisors) {
            if (advisor instanceof RegexMatchAdvisor) {
                //判断是否属于这个类的 通知 是就加入
                if (((RegexMatchAdvisor) advisor).getPointCutResolver().matchsClass(clazz, advisor.getExpression())) {
                    ((RegexMatchAdvisor) advisor).setObj(this.obj);
                    matchAdvisor.add(advisor);
                }
            }
        }
        return matchAdvisor;
    }

    public void register(Advisor advisor) {
        this.advisors.add(advisor);
    }

    public List<Advisor> getAdvisor() {
        return this.advisors;
    }
}
