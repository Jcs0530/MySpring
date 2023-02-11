package com.cs.mvc.bean;

import com.cs.ioc.utils.CastUtil;

import java.util.Collection;
import java.util.Map;

public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Object[] getParams() {
        Collection<Object> values = paramMap.values();
        System.out.println(values);
        return values.toArray();

    }

    public int getInt(String name) {
        return CastUtil.castInt(paramMap.get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castDouble(paramMap.get(name));
    }

    public String getString(String name) {
        return CastUtil.castString(paramMap.get(name));
    }

    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(paramMap.get(name));
    }


    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
