package com.ackerman;

import com.ackerman.dao.NewsDao;
import com.ackerman.dao.UserDao;
import com.ackerman.model.News;
import com.ackerman.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
        News news = new News();
        news.setType(2);
        news.setUserId(5);
        news.setLikeCount(11);
        news.setCommentCount(20);
        news.setTitle("hahahah");
        news.setLink("www.ackerman.com");
        news.setImageLink("www.tupian.com");
        news.setCreateDate(new Date());

        System.out.println("insert news:" + newsDao.addNews(news));
    }
}
