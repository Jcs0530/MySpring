package com.xiaoqiang.mvc.bean;

import java.util.Objects;

/**
 * @author xiaoqiang
 * @date 2019/10/7-20:10
 */
public class Request {

    private RequestMethod requestMethod;

    private String requestPath;

    public Request(RequestMethod requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestMethod=" + requestMethod +
                ", requestPath='" + requestPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request that = (Request) o;
        return requestMethod == that.requestMethod &&
                Objects.equals(requestPath, that.requestPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, requestPath);
    }
}
