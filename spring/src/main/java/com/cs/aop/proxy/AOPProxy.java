package com.cs.aop.proxy;

import com.cs.aop.advisor.Advisor;
import com.cs.aop.advisor.PointCutAdvisor;

import java.util.ArrayList;
import java.util.List;

public interface AOPProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);

    /**
     * 获取所有匹配的通知者(advisor)
     * @param clazz
     * @return
     */
    default List<Advisor> getMatchAdvisors(Class clazz, List<Advisor> advisors) throws Exception {
        List<Advisor> match = new ArrayList<>();
        for(Advisor advisor:advisors){
            if(advisor instanceof PointCutAdvisor){
                PointCutAdvisor pointCutAdvisor = (PointCutAdvisor) advisor;
                if(pointCutAdvisor.getPointCutResolver().matchsClass(clazz, pointCutAdvisor.getExpression())){
                    //匹配到了
                    match.add(advisor);
                }
            }
        }
        return match;
    }
}
