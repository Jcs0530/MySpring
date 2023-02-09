package com.xiaoqiang.ioc.config;

import com.xiaoqiang.ioc.testBean.User;
import com.xiaoqiang.mvc.annotation.Autowired;
import com.xiaoqiang.mvc.annotation.Controller;
import com.xiaoqiang.mvc.annotation.RequestMapping;
import com.xiaoqiang.mvc.annotation.RequestParam;
import com.xiaoqiang.mvc.bean.ModelAndView;

/**
 * @author xiaoqiang
 * @date 2019/10/7-14:58
 */
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
