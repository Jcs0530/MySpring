package com.cs.ioc.utils;

import java.io.File;
import java.net.URL;

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
