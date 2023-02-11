package com.cs.mvc.bean;

import java.lang.reflect.Method;

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
