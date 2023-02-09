package com.xiaoqiang.mvc.bean;

import java.lang.reflect.Method;

/**
 * @author xiaoqiang
 * @date 2019/10/7-20:13
 */
public class RequestHandler {

    private Object controller;

    private Method method;

    public RequestHandler(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Object getControllerClass() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
