package com.ackerman.conf;

import com.ackerman.interceptor.PassportInterceptor;
import com.ackerman.interceptor.SSOInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 上午11:08 18-6-6
 */
@Component
public class CelticsConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private SSOInterceptor ssoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(ssoInterceptor);
    }
}
