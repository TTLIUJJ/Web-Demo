package com.ackerman.service;

import com.ackerman.dao.NewsDao;
import com.ackerman.model.News;
import com.ackerman.utils.JedisUtil;
import com.ackerman.utils.MasterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午9:50 18-6-16
 */
@Service
public class NewsService implements InitializingBean{
    private Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    private MasterUtil masterUtil;

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private NewsDao newsDao;

    public List<News> getNewsByOffsetAndLimit(int offset, int limit){
        return newsDao.getNewsByOffsetAndLimit(offset, limit);
    }

    public News getNewsById(int id){
        return newsDao.getNewsById(id);
    }

    /**
    * @Description: 进程一启动, 先设置hotNews, 便于调试
    * @Date: 上午10:59 18-6-17
    */
    public void initHotNews(){
        try {
            jedisUtil.del(JedisUtil.HOT_NEWS_KEY);
            List<Integer> list = getHotNewsId();
            for(int id : list){
                jedisUtil.rpush(JedisUtil.HOT_NEWS_KEY, String.valueOf(id));
            }
        }catch (Exception e){
            logger.error("设置启动hotNes失败", e);
        }
    }

    /**
    * @Description: 计算出热门新闻, 这里计算前10000条的新闻, 之后改进的, 可以计算一段时间以内
     *                  返回新闻的id编号, 分值大的放前面
    * @Date: 上午9:38 18-6-17
    */
    public List<Integer> getHotNewsId(){

        //分值从大到小
        PriorityQueue<News> queue = new PriorityQueue<>(new Comparator<News>(){
            @Override
            public int compare(News o1, News o2){
                if(o1.getScore() == o2.getScore())
                    return 0;
                return o1.getScore() < o2.getScore() ? 1 : -1;
            }
        });

        List<News> newsList = newsDao.getNewsByOffsetAndLimit(0, 10000);
        for(News news : newsList){
            caculateScore(news);
            queue.add(news);
        }

        ArrayList<Integer> res = new ArrayList<>();
        while(!queue.isEmpty()){
            News news = queue.poll();
            System.out.println("title:" + news.getTitle() +", score:" + news.getScore());
            res.add(news.getId());
        }

        return res;
    }

    //　点赞数, 评论数越多, 创建时间越短, 分值越高
    public void caculateScore(News news){
        try{
            long likeScore = news.getLikeCount()*7;
            long commentScore = news.getCommentCount()*11;
            long createTime = (System.currentTimeMillis() - news.getCreateDate().getTime()) / (1000 * 60 * 10);
            createTime = createTime == 0 ? 1 : createTime;
            long score = (likeScore + commentScore) / createTime;
            news.setScore(score);
        }catch (Exception e){
            logger.error("计算分值错误", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initHotNews();
                Random random = new Random(47);
                masterUtil.start();

                while (true){
                    long sleepMills = masterUtil.waitForMillseconds();
                    System.out.println("睡眠时间: " + sleepMills);
                    try {
                        TimeUnit.MILLISECONDS.sleep(sleepMills);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String nextPath = masterUtil.createNextMasterNodePath();
                    if(masterUtil.createNextMasterNode(nextPath)){
                        String lastPath = masterUtil.getLastMasterNodePath();
                        masterUtil.deleteLastMasterNode(lastPath);

                        List<Integer> list = getHotNewsId();
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0; i < list.size(); ++i){
                            if(i == list.size()-1)
                                sb.append(list.get(i));
                            else
                                sb.append(list.get(i) + ",");
                        }
                        masterUtil.setData(nextPath, sb.toString().getBytes());

                        System.out.println("----------------------------------------------------");
                        System.out.println("设置节点数据成功:" + sb.toString());
                        System.out.println("----------------------------------------------------");
                    }
                    else{
                        try{
                            //等待master设置完数据
                            TimeUnit.SECONDS.sleep(10);
                        }catch (Exception e){}

                        byte []data = masterUtil.fetchData(nextPath);
                        System.out.println("----------------------------------------------------");
                        System.out.println("从节点中获取数据:" + new String(data));
                        System.out.println("----------------------------------------------------");

                        jedisUtil.del(JedisUtil.HOT_NEWS_KEY);
                        String []ids = (new String(data)).split(",");
                        for(String id : ids){
                            jedisUtil.rpush(JedisUtil.HOT_NEWS_KEY, id);
                        }
                    }

                }
            }
        }).start();
    }
}
