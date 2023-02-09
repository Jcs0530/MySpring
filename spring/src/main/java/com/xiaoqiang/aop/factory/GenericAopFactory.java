package com.xiaoqiang.aop.factory;

import com.xiaoqiang.aop.advisor.Advisor;
import com.xiaoqiang.aop.proxy.AOPProxy;
import com.xiaoqiang.aop.proxy.CglibDynamicProxy;
import com.xiaoqiang.ioc.factory.BeanFactory;

import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/10/10-16:55
 */
public class GenericAopFactory implements AOPFactory {

    @Override
    public AOPProxy createAopProxyInstance(Object target, List<Advisor> advisor, String beanName) {
        return new CglibDynamicProxy(target, advisor, beanName);

    }
}
