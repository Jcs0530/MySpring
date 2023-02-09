package com.xiaoqiang.ioc.config;

import com.xiaoqiang.aop.annotation.*;

/**
 * @author xiaoqiang
 * @date 2019/10/10-15:20
 */
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
