package com.xiaoqiang.ioc.config;

import com.xiaoqiang.ioc.annotation.Bean;
import com.xiaoqiang.ioc.annotation.Configuration;
import com.xiaoqiang.ioc.testBean.User;

/**
 * @author xiaoqiang
 * @date 2019/10/6-16:32
 */
@Configuration
public class BeanConfig {
    @Bean
    public User getUser() {
        User user = new User();
        user.setAge(10);
        user.setName("xiaoqiang");
        return user;
    }
}
