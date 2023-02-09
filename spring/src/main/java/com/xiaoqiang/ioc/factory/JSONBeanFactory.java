package com.xiaoqiang.ioc.factory;

import com.alibaba.fastjson.JSON;
import com.xiaoqiang.ioc.bean.BeanDefinition;
import com.xiaoqiang.ioc.utils.ClassLoaderUtils;
import com.xiaoqiang.ioc.utils.JSONUtils;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author xiaoqiang
 * @date 2019/10/5-17:13
 */

// 初始化好了BeanDefinitionMap
public class JSONBeanFactory extends AbstractBeanFactory {

    protected JSONBeanFactory() {}

    public JSONBeanFactory(InputStream in) {
        loadBeanDefinitions(in);
    }

    public JSONBeanFactory(String file) {
        InputStream in = loadFile(file);
        this.loadBeanDefinitions(in);
    }


    protected InputStream loadFile(String file) {
        return ClassLoaderUtils.getClassLoder().getResourceAsStream(file);
    }

    //载入Bean的信息
    public void loadBeanDefinitions(InputStream inputStreams) {

        List objects = (List) JSON.parseArray(JSONUtils.getString(inputStreams));
        for (Object object : objects) {
            // fastjson 填充BeanDefinition对象
            BeanDefinition beanDefinition = JSON.parseObject(object + "", BeanDefinition.class);

            beanDefinitionsMap.put(beanDefinition.getName(), beanDefinition);
//            System.out.println(beanDefinition);
        }
    }
}
