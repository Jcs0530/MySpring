package com.xiaoqiang.ioc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author xiaoqiang
 * @date 2019/10/7-22:02
 */
public class CodecUtil {
    public static final String encodeURL(String source) {
        String target = null;

        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encode url failed");
        }
        return target;
    }

    public static final String
    decodeURL(String source) {
        String target = null;

        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decode url failed");

        }
        return target;
    }
}
