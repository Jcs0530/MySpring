package com.cs.aop.proxy;

import com.cs.aop.advisor.Advisor;
import com.cs.ioc.utils.AOPUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 没有无参构造
 * 主要提供提供一个由Cglib生成的代理对象
 */
public class CglibDynamicProxy implements AOPProxy, MethodInterceptor {
    //加强器 用于生成代理对象
    private Enhancer enhancer = new Enhancer();
    private Object target;
    private List<Advisor> advisors;
    private String beanName;

    public CglibDynamicProxy(Object target, List<Advisor> advisors, String beanName) {
        this.target = target;
        this.advisors = advisors;
        this.beanName = beanName;
    }

    @Override
    public Object getProxy() {
        return getProxy(target.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        enhancer.setSuperclass(target.getClass());
        enhancer.setClassLoader(classLoader);
        enhancer.setInterfaces(target.getClass().getInterfaces());
        enhancer.setCallback(this);
//        Object res = null;

        return enhancer.create();

    }

//    @Override
//    public List<Advisor> getMatchAdvisors(Class clazz, List<Advisor> advisors) throws Exception {
//        return null;
//    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return AOPUtil.applyAdvice(target, o, advisors, objects, method);
    }


}
