package com.xiaoqiang.ioc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaoqiang
 * @date 2019/10/6-20:15
 */
public class StringUtils {
    public static final String lowerFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


    private static String[] validateExpresseion(String expression) throws Exception {
        Pattern compile = Pattern.compile("(?<=\\().+?\\)");
        Matcher matcher = compile.matcher(expression);
        if (!matcher.find()) {
            throw new Exception("表达式错误。");
        }
        String[] split = matcher.group().split(" ");
        if (split.length <= 1) {
            throw new Exception("表达式错误。");
        }
        return split;
    }

    /**
     * 获取表达式的权限符
     *
     * @param expression
     * @return
     * @throws Exception
     */
    public static String matchModifier(String expression) throws Exception {
        String[] split = validateExpresseion(expression);
        return split[0];
    }

    /**
     * 匹配类名
     *
     * @param expression
     * @return
     * @throws Exception
     */
    public static String matchClassName(String expression) throws Exception {
        return match(expression, "(.+?)(?=\\..+\\()");
    }

    public static String matchMethodParam(String expression) throws Exception {
        return match(expression, "(?<=\\()(.*?)(?=\\))");
    }

    public static String matchMethodName(String expression) throws Exception {
        return match(expression, "[^\\.]+?(?=\\()");
    }

    private static String match(String expression, String s) throws Exception {
        String[] split = validateExpresseion(expression);
        String pathName = split[1];
        Pattern compile = Pattern.compile(s);
        Matcher matcher = compile.matcher(pathName);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group());
        }
        return sb.toString();
    }
}
