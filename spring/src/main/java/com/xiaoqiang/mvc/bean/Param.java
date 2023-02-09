package com.xiaoqiang.mvc.bean;

import com.xiaoqiang.ioc.utils.CastUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/10/8-8:24
 */
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
