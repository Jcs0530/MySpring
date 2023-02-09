package com.xiaoqiang.ioc.utils;

import com.xiaoqiang.ioc.bean.BeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xiaoqiang
 * @date 2019/10/5-14:37
 */
public class ClassLoaderUtils {


    //扫描包
    public static final Set<Class<?>> scanPackge(String scanPackge) {
        //存放包类所有的类实例
        Set<Class<?>> classCache = new HashSet<>();
        File[] files = FileUtils.getFiles(scanPackge);
        if (files == null) {
            return classCache;
        }
        for (File file : files) {

            if (file.isDirectory()) {
                scanPackge(scanPackge + "." + file.getName());
            } else {
                Class<?> clazz = loadClass(scanPackge + "." + file.getName().replace(".class", ""), false);
                classCache.add(clazz);
            }
        }
        return classCache;
    }


    public static ClassLoader getClassLoder() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoder());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
