package com.xiaoqiang.ioc.utils;

import java.io.File;
import java.net.URL;

/**
 * @author xiaoqiang
 * @date 2019/10/6-19:42
 */
public class FileUtils {
    public static final File[] getFiles(String packge) {
        URL resource = ClassLoaderUtils.getClassLoder().getResource( packge.replaceAll("\\.", "/"));
//        System.out.println(resource);
        if (resource == null || resource.getFile() == null) {
            return null;
        }
        File dir = new File(resource.getFile());


        return dir.listFiles();
    }
}
