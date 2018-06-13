package com.ackerman.utils;

import com.ackerman.model.User;
import org.springframework.stereotype.Component;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午8:24 18-6-5
 */
@Component
public class HostHolder {
    private ThreadLocal<User> threadLocal = new ThreadLocal<User>();

    public void setUser(User user){
        threadLocal.set(user);
    }

    public User getUser(){
        return threadLocal.get();
    }

    public void remove(){
        threadLocal.remove();
    }
}
