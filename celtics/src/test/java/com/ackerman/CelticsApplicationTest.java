package com.ackerman;

import com.ackerman.dao.NewsDao;
import com.ackerman.dao.UserDao;
import com.ackerman.model.News;
import com.ackerman.model.User;
import com.ackerman.service.SSOService;
import com.ackerman.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午5:04 18-6-4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CelticsApplication.class)
@Sql("/init-sql.sql")
public class CelticsApplicationTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SSOService ssoService;

    @Test
    public void testUser(){
        User user = new User();
        user.setUsername("hello");
        user.setPassword("shit");
        user.setSalt("salt");
        user.setHeadImageUrl("www.baidu.com");

        userDao.addUser(user);
    }

    @Test
    public void testNews(){
        Random random = new Random(47);
        for(int i = 0; i < 20; ++i){
            News news = new News();
            news.setType((i + random.nextInt(17)) % 2);
            news.setUserId(1 + random.nextInt(10));
            news.setLikeCount(random.nextInt(100) + 30);
            news.setCommentCount(random.nextInt(30));
            news.setTitle("titile-" + i);
            news.setLink("www.ackerman.com" + i);
            news.setImageLink("www.tupian.com" + i);
            long time = System.currentTimeMillis();
            news.setCreateDate(new Date(time -  + 1000*60*60 * i));

            newsDao.addNews(news);
        }
    }

    @Test
    public void testCluster(){
        userService.login("liujiajing", "hahaha" );
    }


}
