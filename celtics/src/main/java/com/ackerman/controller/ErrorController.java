package com.ackerman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {
    @RequestMapping("/error")
    @ResponseBody
    public String error() {
        return "fuck";
    }
}
