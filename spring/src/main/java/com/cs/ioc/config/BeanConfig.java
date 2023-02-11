package com.cs.ioc.config;

import com.cs.ioc.annotation.Bean;
import com.cs.ioc.annotation.Configuration;
import com.cs.ioc.testBean.User;

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
