package com.xiaoqiang.aop.factory;

import com.xiaoqiang.aop.advisor.Advisor;
import com.xiaoqiang.aop.proxy.AOPProxy;
import com.xiaoqiang.ioc.factory.BeanFactory;

import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/10/10-16:34
 */
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
