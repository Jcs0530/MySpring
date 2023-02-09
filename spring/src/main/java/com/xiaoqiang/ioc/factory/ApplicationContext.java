package com.xiaoqiang.ioc.factory;

import com.xiaoqiang.aop.advisor.Advisor;
import com.xiaoqiang.aop.advisor.RegexMatchAdvisor;
import com.xiaoqiang.aop.annotation.*;
import com.xiaoqiang.aop.bean.PointCutResover;
import com.xiaoqiang.aop.postprocessor.AopProxyCreator;
import com.xiaoqiang.ioc.annotation.Bean;
import com.xiaoqiang.ioc.annotation.Configuration;
import com.xiaoqiang.ioc.utils.*;
import com.xiaoqiang.mvc.annotation.Autowired;
import com.xiaoqiang.mvc.annotation.Controller;
import com.xiaoqiang.mvc.annotation.RequestMapping;
import com.xiaoqiang.mvc.bean.Request;
import com.xiaoqiang.mvc.bean.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;

/**
 * @author xiaoqiang
 * @date 2019/10/6-17:50
 */
public class ApplicationContext extends JSONBeanFactory {


    private Properties properties = new Properties();

    private Set<Class<?>> classCache = new HashSet<>();

    private Map<Request, RequestHandler> mappingMap = new HashMap<>();

    public Properties getProperties() {
        return properties;
    }
    public Map<Request, RequestHandler> getMappingMap() {
        return mappingMap;
    }

    private ApplicationContext() {
    }

    public ApplicationContext(String properties) {
        init(ClassLoaderUtils.getClassLoder().getResourceAsStream(properties));
    }


    private void init(InputStream in) {
        try {
            //加载配置文件
            properties.load(in);
            //json配置文件
            String jsonConfigs = properties.getProperty(Constant.JSONCONFIG);  // ioc.beans.jsonconfig
            if (jsonConfigs != null) {
                String[] Configs = jsonConfigs.split(",");
                for (String config : Configs) {
                    loadBeanDefinitions(loadFile(config));   // 调用父类方法 初始化BeanDefinitionMap
                }
            }

            String scanPackges = properties.getProperty(Constant.SCAN_PACKGE);  // ioc.scan.packge
            if (scanPackges != null) {
                registerAnnotationBeans(scanPackges.split(","));   // 将配置文件中的包扫描路径下的类 全部加载 并放入classCache中
            }

            doCreateBeans();
            dealAnnotationBeans();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实例化包扫描下的类，并解析其标注的注解
     */
    private void doCreateBeans() {
        //
        if (classCache.isEmpty()) {
            return;
        }
        // 遍历包下的所有class信息
        for (Class<?> clazz : classCache) {
            if (clazz.getAnnotations().length == 0) {
                continue;   // 若没有注解 直接跳过
            } else if (clazz.isAnnotationPresent(Configuration.class)) {   // 如果类上加了@Configuration注解
                Object o = ReflectionUtils.newInstance(clazz);   // 根据类型信息反射创建对象实例
                Method[] methods = clazz.getMethods();           // 获取该类下的所有Method对象
                // 遍历Method数组
                for (Method method : methods) {
                    // 对方法上加了@Bean注解的方法进行处理
                    if (method.isAnnotationPresent(Bean.class)) {
                        Bean beanAnnotation = method.getAnnotation(Bean.class); // 获取注解对象
                        String name = beanAnnotation.value();                   // 获取注解对象传递的参数
                        if ("".equals(name.trim())) {
                            name = StringUtils.lowerFirstChar(method.getReturnType().getSimpleName());  // 若没传递参数，以类名为类信息id
                        }
                        Object bean = ReflectionUtils.invoke(method, o);       // 执行该方法对应逻辑得到返回的对象

                        classCacheObjectsMap.put(bean.getClass(), bean);       // 注册类信息
                        singletonObjectsMap.put(name, bean);                   // 注册单例实例
                    }
                }
            } else if (clazz.isAnnotationPresent(Controller.class)) {     // 如果类上加了 @Controller注解
                String url = "";                                          // 访问url
                if (clazz.isAnnotationPresent(RequestMapping.class)) {    // 类上如果加了@RequestMapping注解
                    RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
                    url = annotation.value();                             // 更新url
                }

                Object o = ReflectionUtils.newInstance(clazz);            // 无参构成创建对象实例

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {  // 检查方法上是否添加@RequestMapping注解
                        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                        Request request = new Request(annotation.method(), annotation.value());   // 记录request对象
                        RequestHandler handler = new RequestHandler(o, method);                   // 记录requestHandle对象
                        mappingMap.put(request, handler);                                         // 保持请求对象和请求处理对象放入哈希表中
                    }
                }
                classCacheObjectsMap.put(clazz, o);            // 注册类信息
            } else if (clazz.isAnnotationPresent(Aspect.class)) {
                addPostProcessor(clazz);    // 进行AOP增强
            } else {
                Object o = ReflectionUtils.newInstance(clazz);  // 没有注解直接实例化
                classCacheObjectsMap.put(clazz, o);
            }

        }
    }

    /**
     * 这个方法处理扫描中的成员属性上叫的注解
     */
    private void dealAnnotationBeans() {
        for (Class<?> clazz : classCacheObjectsMap.keySet()) {    // 遍历注册成功所有Bean的class信息
            String name = StringUtils.lowerFirstChar(clazz.getSimpleName());
            Object o = classCacheObjectsMap.get(clazz);         // 获取对象实例
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Autowired annotation = field.getAnnotation(Autowired.class);
                if (annotation != null) {
                    String value = annotation.value().trim();
                    if ("".equals(value)) {
                        ReflectionUtils.setField(o, field, classCacheObjectsMap.get(field.getType()));
                    } else {
                        ReflectionUtils.setField(o, field, singletonObjectsMap.get(value));
                    }
                }
            }
            singletonObjectsMap.put(name, o);
        }

    }


    /**
     *
     *
     * @param clazz
     */
    private void addPostProcessor(Class<?> clazz) {
        // new 一个
        AopProxyCreator aopProxyCreator = new AopProxyCreator();
        // 获取该类下的所有方法
        Method[] methods = clazz.getMethods();
        // 创建对象实例
        Object o = ReflectionUtils.newInstance(clazz);
        // 赋值给aopProxyCreator对象
        aopProxyCreator.setObj(o);
        // new一个切点表达匹配对象
        PointCutResover pointCutResover = new PointCutResover();
        //
        Map<String, String> expressions = new HashMap<>();
        // 遍历该类所有方法数组 解析方法上叫的注解
        for (Method method : methods) {
            if (method.isAnnotationPresent(PointCut.class)) {
                //加入切点
                PointCut pointCut = method.getDeclaredAnnotation(PointCut.class);
                String expression = pointCut.value();
                expressions.put(method.getName(), expression);
            } else if (method.isAnnotationPresent(Before.class)) {
                Before before = method.getAnnotation(Before.class);
                String value = before.value();  // test
                RegexMatchAdvisor beforeAdvice = new RegexMatchAdvisor("BeforeAdvice", expressions.get(value), pointCutResover,method);
                aopProxyCreator.register(beforeAdvice);
            }else if (method.isAnnotationPresent(After.class)) {
                After after = method.getAnnotation(After.class);
                String value = after.value();
                RegexMatchAdvisor afterAdvice = new RegexMatchAdvisor("AfterAdvice", expressions.get(value), pointCutResover, method);
                aopProxyCreator.register(afterAdvice);
            }else if (method.isAnnotationPresent(Around.class)) {
                Around around = method.getAnnotation(Around.class);
                String value = around.value();
                RegexMatchAdvisor aroundAdvice = new RegexMatchAdvisor("AroundAdvice", expressions.get(value), pointCutResover, method);
                aopProxyCreator.register(aroundAdvice);
            }
        }
        aopPostProcessors.add(aopProxyCreator);

    }

    private void registerAnnotationBeans(String[] scanPackges) {
        for (String scanPackge : scanPackges) {
            classCache.addAll(ClassLoaderUtils.scanPackge(scanPackge));
        }
    }


}
