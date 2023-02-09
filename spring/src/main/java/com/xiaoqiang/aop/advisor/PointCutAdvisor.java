package com.xiaoqiang.aop.advisor;

import com.xiaoqiang.aop.bean.PointCut;

import java.lang.reflect.Method;

/**
 * @author xiaoqiang
 * @date 2019/10/10-16:20
 */
public interface PointCutAdvisor extends Advisor {
    //获取切点
    PointCut getPointCutResolver();


}
