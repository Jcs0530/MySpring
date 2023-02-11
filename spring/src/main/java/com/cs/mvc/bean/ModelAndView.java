package com.cs.mvc.bean;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private Map<String, Object> model = new HashMap<>();
    private String path;

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String viewName;

    public Map<String, Object> getModel() {
        return model;
    }

    public void put(String name, Object obj) {
        model.put(name, obj);
    }

    public String getViewName() {

        return viewName;
    }


    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
