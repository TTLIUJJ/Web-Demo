package com.ackerman.utils;

import com.ackerman.service.NewsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class TestSpringBean implements InitializingBean {
    static {
        System.out.println("---------------------hello world---------------------");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("---------------------hello world---------------------");
    }
}
