package com.cs.ioc.factory;

import com.cs.ioc.bean.BeanDefinition;

public interface BeanFactory {
    Object getBean(String name);

    BeanDefinition getBeanDefinition(String beanName);
}
