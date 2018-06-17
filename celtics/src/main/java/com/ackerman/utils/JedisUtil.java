package com.ackerman.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @Author: Ackerman
 * @Description: Redis‘s API
 * @Date: Created in 下午11:25 18-6-4
 */
@Component
public class JedisUtil {
    private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    private static JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);

    private static JedisCluster jedisCluster;
    static{
        HashSet<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("127.0.0.1", 7000));
        set.add(new HostAndPort("127.0.0.1", 8000));
        set.add(new HostAndPort("127.0.0.1", 9000));
        jedisCluster = new JedisCluster(set);
    }

    public static final String HOT_NEWS_KEY = "hot-news";

    public void _setex(String key, int seconds, String val){
        try{
            jedisCluster.setex(key, seconds, val);
        }catch (Exception e){
            logger.error("JedisCluster.set()", e);
        }
    }

    public String _get(String key){
        try{
            return jedisCluster.get(key);
        }catch (Exception e){
            logger.error("jedisCluster.get()", e);
        }
        return null;
    }

    public void _del(String key){
        try{
            jedisCluster.del(key);
        }catch (Exception e){
            logger.error("jedisCluster.del()", e);
        }
    }

    /*
    ---------------------------------------------------------------------------------------
     */

    public void del(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.del(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null)
                jedis.close();
        }
    }


    public void rpush(String key,String val){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.rpush(key, val);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null)
                jedis.close();
        }
    }

    public long llen(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.llen(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public String lindex(String key, long index){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.lindex(key, index);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return null;
    }

}
