package com.ackerman.service;

import com.ackerman.dao.UserDao;
import com.ackerman.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午10:28 18-6-4
 */
@Service
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public int addUser(String username, String password){
        int ret = 0;
        try{
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setSalt("aaaaa");
            user.setHeadImageUrl("www.google.com");
            ret = userDao.addUser(user);
        }catch (Exception e){
            logger.error("添加用户失败, ", e);
        }

        return ret;
    }
}
