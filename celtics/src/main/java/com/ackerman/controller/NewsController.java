package com.ackerman.controller;

import com.ackerman.model.News;
import com.ackerman.service.NewsService;
import com.ackerman.utils.JedisUtil;
import com.ackerman.utils.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午11:18 18-6-16
 */
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private JedisUtil jedisUtil;


    @RequestMapping(path = {"/news"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(@RequestParam(value = "offset", defaultValue = "0") int offset,
                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                        Model model){
        try{
            List<ViewObject> vos = new ArrayList<>(limit);
            List<News> newsList = newsService.getNewsByOffsetAndLimit(0, limit);
            for(int i = 0; i < newsList.size(); ++i){
                ViewObject vo = new ViewObject();
                vo.set("index", i+1+offset);
                vo.set("news", newsList.get(i));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "news";
    }

    @RequestMapping(path = "/hot", method = RequestMethod.GET)
    public String hot(@RequestParam(value = "offset", defaultValue = "0") int offset,
                      @RequestParam(value = "limit", defaultValue = "10") int limit,
                      Model model){
        try{
            List<ViewObject> vos = new ArrayList<>(limit);
            long len = jedisUtil.llen(JedisUtil.HOT_NEWS_KEY);
            for(long i = 0; i < len; ++i){
                ViewObject vo = new ViewObject();

                vo.set("index", i+1+offset);

                String newsId = jedisUtil.lindex(JedisUtil.HOT_NEWS_KEY, i);
                News news = newsService.getNewsById(Integer.valueOf(newsId));
                vo.set("news", news);

                vos.add(vo);
            }
            model.addAttribute("vos", vos);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "news";
    }



}
