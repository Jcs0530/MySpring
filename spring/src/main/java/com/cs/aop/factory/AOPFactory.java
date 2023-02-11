package com.cs.aop.factory;

import com.cs.aop.advisor.Advisor;
import com.cs.aop.proxy.AOPProxy;

import java.util.List;

public interface AOPFactory {
    AOPProxy createAopProxyInstance(Object target, List<Advisor> advisor, String beanName);

    /**
     * 用于判断使用哪种代理方式来完成增强功能
     * 简单判断：类实现了接口就用JDK代理 没实现接口就用cglib代理
     * @param target
     * @return
     */
    default boolean judgeUseWhichProxyMode(Object target){
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return interfaces.length>0;
    }

    //不通过创建对象 直接调用
    static AOPFactory createProxyInstance(){
        return new GenericAopFactory();
    }


}
