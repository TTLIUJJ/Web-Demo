package com.ackerman.controller;

import com.ackerman.UserModel;
import com.ackerman.model.User;
import com.ackerman.service.SSOService;
import com.ackerman.service.UserService;
import com.ackerman.utils.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午10:26 18-6-4
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SSOService ssoService;



    @RequestMapping(path = "/doRegister", method = RequestMethod.POST)
    public String doRegister(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam(value = "remember", defaultValue = "0") int remember,
                             HttpServletResponse response){
        //remember = 1代表记住登录

        int id = userService.register(username, password);
        if(id != 0 && remember == 1){
            userService.addCookie(response, id);
        }

        return "index";
    }

    @RequestMapping(path = "/doLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam(value = "remember", defaultValue = "0") int remember,
                          HttpServletRequest request){

//        int id = userService.login(username, password);
//
//        if(id != 0 && remember == 1){
//            userService.addCookie(response, id);
//        }

        UserModel userModel = ssoService.loginViaPassword(request, username, password);

        return "index";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        userService.logout(request);

        return "redirect:index";
    }
}
