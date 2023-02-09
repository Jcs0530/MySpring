package com.xiaoqiang.ioc.bean;

import java.util.List;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/10/5-10:57
 */
public class
BeanDefinition {

    // 类信息标识
    private String name;
    // 类的全限定名
    private String className;
    // 若接口 则为接口全限定名
    private String interfaceName;
    // 类构造器集合
    private List<ConstructorArg> constructorArgs;
    // 类属性集合
    private List<PropertyArg> propertyArgs;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }


    public List<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(List<ConstructorArg> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }


    public List<PropertyArg> getPropertyArgs() {
        return propertyArgs;
    }

    public void setPropertyArgs(List<PropertyArg> propertyArgs) {
        this.propertyArgs = propertyArgs;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", constructorArgs=" + constructorArgs +
                ", propertyArgs=" + propertyArgs +
                '}';
    }
}
