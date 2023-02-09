package com.xiaoqiang.aop.advisor;

import java.lang.reflect.Method;

/**
 * @author xiaoqiang
 * @date 2019/10/10-16:19
 */
public interface Advisor {
    String getAdviceBeanName();
    String getExpression();
    Method getMethod();
}
