package com.cs.aop.advisor;

import java.lang.reflect.Method;

public interface Advisor {
    String getAdviceBeanName();
    String getExpression();
    Method getMethod();
}
