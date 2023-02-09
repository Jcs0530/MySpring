package com.xiaoqiang.ioc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/10/6-10:09
 */
public interface Constant {
    Map<String,Class<?>> CLASS_MAP = new HashMap<String,Class<?>>(){{
        put("String", String.class);
        put("int", int.class);
        put("double", double.class);
        put("char", char.class);
        put("short", short.class);
        put("long", long.class);
        put("float", float.class);
        put("Integer", Integer.class);
        put("Double", Double.class);
        put("Character", Character.class);
        put("Short", Short.class);
        put("Long", Long.class);
        put("Float", Float.class);
    }};

    String JSONCONFIG = "ioc.beans.jsonconfig";

    String SCAN_PACKGE = "ioc.scan.packge";
    String JSP_PATH = "ioc.jsp_path";
    String ASSET_PATH = "ioc.static_path";

}
