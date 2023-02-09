package com.xiaoqiang.ioc.factory;

import com.xiaoqiang.ioc.bean.BeanDefinition;

/**
 * @author xiaoqiang
 * @date 2019/10/5-11:16
 */
public interface BeanFactory {
    Object getBean(String name);

    BeanDefinition getBeanDefinition(String beanName);
}
