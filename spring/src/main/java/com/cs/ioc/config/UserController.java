package com.cs.ioc.config;

import com.cs.ioc.testBean.User;
import com.cs.mvc.annotation.Autowired;
import com.cs.mvc.annotation.Controller;
import com.cs.mvc.annotation.RequestMapping;
import com.cs.mvc.annotation.RequestParam;
import com.cs.mvc.bean.ModelAndView;

@Controller
public class UserController {

    @Autowired
    public User user;

    @RequestMapping("/hello")
    public ModelAndView test(@RequestParam("param") String param) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(param);
        modelAndView.put("test", "hello");
        modelAndView.setPath("index");
        modelAndView.setViewName("json");
        testAop();
        return modelAndView;
    }

    public void testAop() {
        System.out.println("testAop");
    }


}
