package com.xiaoqiang.aop.chain;

import com.xiaoqiang.aop.advice.Advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author xiaoqiang
 */

public class AopAdviceChain {

    private Method nextMethod;
    private Method method;
    //目标类
    private Object target;
    private Object[] args;
    //被代理的类
    private Object proxy;
    private List<Advice> advices;


    //通知的索引 记录执行到第多少个advice
    private int index = 0;

    public AopAdviceChain(Method method, Object target, Object[] args, Object proxy, List<Advice> advices) {
        try {
            //下一个执行的 invoke
            nextMethod = AopAdviceChain.class.getMethod("invoke", null);

        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        this.method = method;
        this.target = target;
        this.args = args;
        this.proxy = proxy;
        this.advices = advices;
    }

    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        if (index < this.advices.size()) {
            Advice advice = this.advices.get(index++);
            if ("BeforeAdvice".equals(advice.getAdviceType())) {
                //前置增强 直接执行
                advice.before(args, target, method);
            } else if ("AroundAdvice".equals(advice.getAdviceType())) {
                //环绕增强
                return advice.around(nextMethod, args, this);
            } else if ("AfterAdvice".equals(advice.getAdviceType())) {
                //后置增强
                //如果是后置增强需要先取到返回值
                Object res = this.invoke();
                advice.after(args, target, res, method);
                //后置增强后返回  否则会多执行一次
                return res;
            }
            return this.invoke();
        } else {
            return method.invoke(target, args);
        }
    }
}
