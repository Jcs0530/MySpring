package com.xiaoqiang.ioc.utils;

import com.xiaoqiang.aop.advice.Advice;
import com.xiaoqiang.aop.advice.AdviceImpl;
import com.xiaoqiang.aop.advisor.Advisor;
import com.xiaoqiang.aop.advisor.PointCutAdvisor;
import com.xiaoqiang.aop.advisor.RegexMatchAdvisor;
import com.xiaoqiang.aop.chain.AopAdviceChain;
import com.xiaoqiang.ioc.factory.BeanFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/10/10-16:41
 */
public class AOPUtil {


    public static Object applyAdvice(Object target, Object proxy, List<Advisor> advisors, Object[] args, Method method) throws Exception {
        List<Advice> advices = getMatchMethodAdvice(method, target.getClass(), advisors);
        if (advices.isEmpty()) {
            //没有匹配的增强器 即通知 直接执行方法
            return ReflectionUtils.invoke(method, target, args);
        } else {
//存在增强器
            return new AopAdviceChain(method, target, args, proxy, advices).invoke();

        }
    }

    /**
     * 获取与方法匹配的advice
     *
     * @param method
     * @param aClass
     * @param advisors
     * @return 通知列表
     */
    public static List<Advice> getMatchMethodAdvice(Method method, Class<?> aClass, List<Advisor> advisors) throws Exception {
        if (advisors.isEmpty()) {
            return null;
        }
        List<Advice> advices = new ArrayList<>();
        for (Advisor advisor : advisors) {
            if (advisor instanceof RegexMatchAdvisor) {
                RegexMatchAdvisor pointCutAdvisor = (RegexMatchAdvisor) advisor;
                //判断是不是该类的 增强器
                boolean res = pointCutAdvisor.getPointCutResolver().matchsMethod(aClass, method, advisor.getExpression());
                if (res) {
                    //找到要加强的类 该类的另一个名字为 增强器的名称
//                    advices.add((Advice) beanFactory.getBean(advisor.getAdviceBeanName()));
                    AdviceImpl advice = new AdviceImpl(advisor.getMethod(), advisor.getAdviceBeanName(),pointCutAdvisor.getObj());
                    advices.add(advice);
                }
            }
        }
        return advices;
    }
}

