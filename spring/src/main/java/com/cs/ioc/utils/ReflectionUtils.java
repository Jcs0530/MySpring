package com.cs.ioc.utils;


import com.cs.mvc.annotation.RequestParam;
import com.cs.mvc.bean.Param;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {

    public static Object newInstance(Class<?> clazz, Class<?>[] classArray, Object[] valueArray) {
        Object instance = null;

        try {
            instance = clazz.getConstructor(classArray).newInstance(valueArray);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static Object newInstance(Class<?> clazz) {
        Object instance = null;

        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }


    public static void setField(Object obj, Field field, String value) {
        try {
            field.setAccessible(true); // 无视权限控制符
            Object o = Cast(value, field.getType()); // 类型转换
            field.set(obj, o); // 设置属性
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static final Object invoke(Method method, Object obj, Object... args) {
        Object o = null;

        try {

            o =method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    public  static boolean isEmpty(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param value
     * @param c
     * @return
     */
    public static Object Cast(String value, Class<?> c) {
        if ("java.lang.Integer".equals(c.getName()) || "int".equals(c.getName())) {
            return Integer.parseInt(value);
        } else if ("java.lang.Long".equals(c.getName()) || "long".equals(c.getName())) {
            return Long.parseLong(value);
        }else if ("java.lang.Double".equals(c.getName()) || "double".equals(c.getName())) {
            return Double.parseDouble(value);
        }else if ("java.lang.Float".equals(c.getName()) || "float".equals(c.getName())) {
            return Float.parseFloat(value);
        } else if ("java.lang.Short".equals(c.getName()) || "short".equals(c.getName())) {
            return Short.parseShort(value);

        } else {
            return value;
        }
    }

    public static Object invokeMappingMethod(Method method, Object controllerClass, Param param) {
        Parameter[] parameters = method.getParameters();
        Map<String, Object> paramMap = param.getParamMap();
        List<Object> list = new ArrayList<>();

        for (Parameter parameter : parameters) {
            RequestParam annotation = parameter.getAnnotation(RequestParam.class);

            if (annotation != null) {
                String value = annotation.value();
                list.add(paramMap.get(value));
            }
        }
        return invoke(method, controllerClass, list.toArray());
    }


}
