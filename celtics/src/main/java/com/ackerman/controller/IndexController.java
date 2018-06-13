package com.ackerman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午2:36 18-6-4
 */
@Controller
public class IndexController {

    @RequestMapping(path = {"/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(){
        return "index";
    }

    @RequestMapping(path = "/seckill", method = {RequestMethod.GET, RequestMethod.POST})
    public String seckill(){
        return "seckill";
    }


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(){
        return "register";
    }

}
