package com.cs.ioc.factory;

import com.cs.aop.postprocessor.PostProcessor;
import com.cs.ioc.bean.BeanDefinition;
import com.cs.ioc.bean.ConstructorArg;
import com.cs.ioc.bean.PropertyArg;
import com.cs.ioc.utils.ClassLoaderUtils;
import com.cs.ioc.utils.Constant;
import com.cs.ioc.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements BeanFactory {

    // 1、定义单例map key为beanID value为对象引用
    protected final ConcurrentHashMap<String, Object> singletonObjectsMap = new ConcurrentHashMap<>();
    // 2、定义存储类信息map key为beanID value为BeanDefinition对象
    protected final ConcurrentHashMap<String, BeanDefinition> beanDefinitionsMap = new ConcurrentHashMap<>();
    // 3、定义存储class实例的map。 key为class实例，value为class实例引用
    protected final Map<Class<?>, Object> classCacheObjectsMap = new HashMap<>();

    //记录观察者
    protected List<PostProcessor> aopPostProcessors = new ArrayList<>();


    /**
     * 在单例map中尝试获取对象实例，没有创建
     * @param name
     * @return 进行过AOP处理后的实例
     */
    @Override
    public Object getBean(String name) {
        // 尝试在单例map中获取单例实例
        Object bean = getSingleton(name);
        // 1、若已经存在单例实例 直接进行aop增强
        if (bean != null) {
            bean = applyAopBeanPostProcessor(bean, name);
            return bean;
        }
        // 2、若不存在 准备通过类信息反射创建实例
        // 在类信息map中获取类信息
        BeanDefinition beanDefinition = beanDefinitionsMap.get(name);
        // 找不到类信息，没办法创建实例 抛出异常
        if (beanDefinition == null) {
            throw new RuntimeException("can't find bean " + name);
        }
        // 根据类型再通过反射创建出对象实例
        bean = createBean(beanDefinition);
        // 将加载后的class实例保持再map中
        classCacheObjectsMap.put(bean.getClass(), bean);
        // 填充对象属性
        populateBean(bean, beanDefinition);

        // 将创建好的对象实例 放入单例map中
        singletonObjectsMap.put(beanDefinition.getName(), bean);

        //进行AOP处理
        bean = applyAopBeanPostProcessor(bean, name);
        return bean;
    }

    /** 进行AOP处理
     *
     * @param bean
     * @param name
     * @return
     */
    protected  Object applyAopBeanPostProcessor(Object bean, String name){
        try {
            for (PostProcessor aopPostProcessor : aopPostProcessors) {
                bean = aopPostProcessor.postProcessWeaving(bean, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 对bean进行属性填充方法
     *
     * @param bean
     * @param beanDefinition
     */
    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        // 尝试获取bean的属性集合
        List<PropertyArg> propertyArgs = beanDefinition.getPropertyArgs();
        // 创建属性的哈希表 key为属性名 value为属性信息对象
        Map<String, PropertyArg> propertyArgMap = new HashMap<>();
        // 1、获取成功开始填充属性map
        if (propertyArgs != null) {
            for (PropertyArg propertyArg : propertyArgs) {
                propertyArgMap.put(propertyArg.getName(), propertyArg);
            }
        } else {// 获取失败 则对象没有成员属性需要填充直接返回
            return;
        }
        // 通过反射获取对象全部属性
        Field[] fields = bean.getClass().getDeclaredFields();
        // 遍历属性数组 为需要填充的属性赋值
        for (Field field : fields) {
            // 通过属性名获取 属性信息对象
            PropertyArg propertyArg = propertyArgMap.get(field.getName());
            // 若属性信息是基本数据类型
            if (propertyArg != null && propertyArg.getValue() != null) {
                ReflectionUtils.setField(bean, field, propertyArg.getValue());
            } else {
                // 若属性信息是引用类型
                if (propertyArg.getRef() == null) {
                    throw new RuntimeException("can't populateBean");
                }
                ReflectionUtils.setField(bean, field, getBean(propertyArg.getRef()));
            }
        }
    }

    /**
     * 根据类信息反射出对象实例
     * @param beanDefinition
     * @return
     */
    private Object createBean(BeanDefinition beanDefinition) {
        // 根据类全限定名加载类
        Class<?> clazz = ClassLoaderUtils.loadClass(beanDefinition.getClassName(), false);
        // 获取BeanDefinition中的构造器信息对象集合
        List<ConstructorArg> constructorArgs = beanDefinition.getConstructorArgs();
        // 若有配置构造器
        if (constructorArgs != null) {
            // 参数类型集合
            List<Class<?>> classList = new ArrayList<>();
            // 参数值集合
            List<Object> objectList = new ArrayList<>();
            // 遍历构造器信息对象集合
            for (ConstructorArg constructorArg : constructorArgs) {
                if (constructorArg != null) {
                    // 获取构造器信息对象中的value值
                    String value = constructorArg.getValue();
                    // 获取成功
                    if (value != null) {
                        // 获取参数类型的Class对象
                        Class<?> c = Constant.CLASS_MAP.get(constructorArg.getType());
                        classList.add(c);
                        // 将解析到的String，转化成对应的类型
                        Object obj = ReflectionUtils.Cast(value, c);
                        objectList.add(obj);
                    } else {
                        // 获取失败，则为引用类型
                        String ref = constructorArg.getRef();
                        // 获取/创建对应的对象实例
                        Object bean = getBean(ref);
                        if (bean != null) {
                            // 放入集合
                            classList.add(bean.getClass());
                            objectList.add(bean);
                        }

                    }
                }
            }
            Class<?>[] classes = new Class[classList.size()];
            classList.toArray(classes);
            // 有参构造实例化
            return ReflectionUtils.newInstance(clazz, classes, objectList.toArray());
        } else {
            // 若没有配置构造器，直接参构造实例化
            return ReflectionUtils.newInstance(clazz);
        }
    }

    private Object getSingleton(String name) {
        return singletonObjectsMap.get(name);
    }

    protected void registerBean(String name, BeanDefinition bd) {
        beanDefinitionsMap.put(name, bd);
    }
    public Map<String, Object> getSingletonObjectsMap() {
        return singletonObjectsMap;
    }

    public Map<String, BeanDefinition> getBeanDefinitionsMap() {
        return beanDefinitionsMap;
    }

    public Map<Class<?>, Object> getClassCacheObjectsMap() {
        return classCacheObjectsMap;
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionsMap.get(beanName);
    }
}
