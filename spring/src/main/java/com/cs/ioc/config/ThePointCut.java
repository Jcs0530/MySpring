package com.cs.ioc.config;

import com.cs.aop.annotation.*;

@Aspect
public class ThePointCut {
    @PointCut("execution(* com.xiaoqiang.ioc.config.UserController.testAop(..))")
    public void test() {
    }

    @Before("test")
    public void before() {
        System.out.println("before");
    }

    @After("test")
    public void after() {
        System.out.println("after");
    }

    @Around("test")
    public void around() {
        System.out.println("around");
    }

}
