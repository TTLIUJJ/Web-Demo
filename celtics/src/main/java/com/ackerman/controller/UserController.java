package com.ackerman.controller;

import com.ackerman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午10:26 18-6-4
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/doRegister", method = RequestMethod.POST)
    public String doRegister(@RequestParam("username") String username,
                             @RequestParam("password") String password){

        int res = userService.addUser(username, password);

        return "redirect:/index";
    }
}
