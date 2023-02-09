package com.xiaoqiang.aop.postprocessor;

/**
 * @author xiaoqiang
 * @date 2019/10/10-15:28
 */
public interface PostProcessor {
    Object postProcessWeaving(Object bean, String beanName) throws Exception;
}
