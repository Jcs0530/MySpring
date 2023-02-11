package com.cs.aop.advisor;

import com.cs.aop.bean.PointCut;

public interface PointCutAdvisor extends Advisor {
    //获取切点
    PointCut getPointCutResolver();


}
