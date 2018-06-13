package com.ackerman.utils;

import com.ackerman.UserModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 上午10:54 18-6-8
 */
@Component
public class LocalInfo {
    private ThreadLocal<Map<String, Object>> threadLocal;

    private Map<String, Object> info;

    public LocalInfo(){
        threadLocal = new ThreadLocal<>();
        info = new HashMap<>();
        threadLocal.set(info);
    }

    public void setAttribute(String key, Object val){
        info.put(key, val);
    }

    public Object getAttribute(String key){
        return info.get(key);
    }

    public void remove(){
        threadLocal.remove();
    }
}
