package com.cs.aop.postprocessor;

public interface PostProcessor {
    Object postProcessWeaving(Object bean, String beanName) throws Exception;
}
