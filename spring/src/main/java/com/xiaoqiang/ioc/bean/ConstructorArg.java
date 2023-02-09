package com.xiaoqiang.ioc.bean;

/**
 * @author xiaoqiang
 * @date 2019/10/5-11:04
 */
public class ConstructorArg {

    private String ref;

    private String name;

    private String value;

    private String type;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConstructorArg{" +
                "ref='" + ref + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
