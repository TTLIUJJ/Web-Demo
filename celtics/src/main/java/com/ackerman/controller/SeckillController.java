package com.ackerman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午3:23 18-6-4
 */
@Controller
public class SeckillController {

    @RequestMapping(path = {"/seckillArgs"}, method = RequestMethod.POST)
    public String seckillCheck(@RequestParam(value = "goodsNum", defaultValue = "0") int goodsNum,
                               @RequestParam(value = "peerNum", defaultValue = "0") int peerNum,
                               ModelAndView modelAndView){

        System.out.println(goodsNum + ", " + peerNum);

        modelAndView.addObject("goodsNum", goodsNum);
        modelAndView.addObject("peerNum", peerNum);

        return "/seckillType";
    }

}
