package com.cs.aop.factory;

import com.cs.aop.advisor.Advisor;
import com.cs.aop.proxy.AOPProxy;
import com.cs.aop.proxy.CglibDynamicProxy;

import java.util.List;

public class GenericAopFactory implements AOPFactory {

    @Override
    public AOPProxy createAopProxyInstance(Object target, List<Advisor> advisor, String beanName) {
        return new CglibDynamicProxy(target, advisor, beanName);

    }
}
