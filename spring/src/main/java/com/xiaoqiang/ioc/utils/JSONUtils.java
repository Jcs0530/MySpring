package com.xiaoqiang.ioc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author xiaoqiang
 * @date 2019/10/5-21:25
 */
public class JSONUtils {
    public static final String getString(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String s = null;
        try {
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb + "";
    }

}
