package com.ackerman.interceptor;

import com.ackerman.controller.IndexController;
import com.ackerman.model.User;
import com.ackerman.service.UserService;
import com.ackerman.utils.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 上午9:41 18-6-6
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{
    private static final Logger logger = LoggerFactory.getLogger(PassportInterceptor.class);
    private static final String CELTICS_TOKEN = "celtics_token";

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String celtics_token = null;
        try{
            for(Cookie cookie : httpServletRequest.getCookies()){
                if(cookie.getName().equals(CELTICS_TOKEN)){
                    celtics_token = cookie.getValue();
                    System.out.println("cookieName: " + cookie.getName());
                    break;
                }
            }

            if(celtics_token != null){
                User user = userService.parseUserFromToken(celtics_token);
                if(user != null){
                    hostHolder.setUser(user);
                }
            }
        }catch (Exception e){
            logger.error("拦截器异常", e);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && hostHolder.getUser() != null){
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.remove();
    }
}
