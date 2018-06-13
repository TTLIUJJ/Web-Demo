package com.ackerman.service;

import com.ackerman.Common;
import com.ackerman.UserModel;
import com.ackerman.utils.LocalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午11:22 18-6-6
 */
@Service
public class SSOService {
    public static final String GLOBAL_TICKET = "celtics_ticket";        //全局
    public static final String LOCAL_TICKET = "celtics_ticket_sport";   //局部
    public static final String CELTICS_TOKEN = "celtics_token";         //免登陆操作
    public static final int SESSION_TIME = 60 * 60 * 2;                 //会话时间, 单位: s　


    private static Logger logger = LoggerFactory.getLogger(SSOService.class);

    @Autowired
    private LocalInfo localInfo;

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("sso.xml");
    private Common common = (Common) context.getBean("impl");

    public void say(String s){
        System.out.println(common.say(s));
    }




    public boolean isLocalSession(String type, String ticket){
        return common.isLocalSession(type, ticket);
    }

    public int getUserByTicket(String type, String ticket){
        return common.getUserFromTicket(type, ticket);
    }

    public String createTicket(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void setTicketExpireTime(String type, String ticket, int id, int seconds){
        common.setTicketExpireTime(type, ticket, id, seconds);
    }


    public void ssoTest(){
        UserModel userModel = common.getUserByid(2);
        System.out.println(userModel);
    }



    /**
    * @Description: 验证ticket是否有效, 如果有效, 更新过期时间
     * @Param（tryp: ticket的类型
     * @Param（ticket: ticket的值
     * @Return: 是否有效
    */
    public boolean verifyTicketOrUpdate(String type, String ticket){
        return common.verifyTicketOrUpdate(type, ticket);
    }

    public boolean verifyToken(String type, String token){
        return common.verifyToken(type, token);
    }

    /**
    * @Description: 通过帐号密码登陆, 需要进入几点操作：
     *                                  1. 验证帐号密码的正确性, 如果正确, 返回wUserModel
     *                                  2. 创建全局会话
     *                                  3. 创建局部会话
     *                              注意: 需要将user保存在localUser中
    * @Date: 上午11:38 18-6-8
    */
    public UserModel loginViaPassword(HttpServletRequest request, String username, String password){
        UserModel userModel = common.loginViaPassword(username, password);
        if(userModel == null || userModel.getId() <= 0) {
            logger.warn("userModel is null or zero");
        }
        else {
            createLocalSession(request, LOCAL_TICKET, userModel);
            createGlobalSession(GLOBAL_TICKET, userModel);
        }

        return userModel;
    }

    public void createLocalSession(HttpServletRequest request, String type, UserModel userModel){
        try{
            String ticket = common.creteTicket(type, userModel.getId());
            HttpSession session = request.getSession();
            session.setAttribute(type, ticket);
            session.setMaxInactiveInterval(SESSION_TIME);

            localInfo.setAttribute("userModel", userModel);
        }catch (Exception e){
            logger.error("创建全局会话异常", e);
        }
    }

    public void createGlobalSession(String type, UserModel userModel){
        try{
            String ticket = common.creteTicket(type, userModel.getId());
            localInfo.setAttribute("global", ticket);
        }catch (Exception e){
            logger.error("创建全局会话异常", e);
        }
    }

    /**
    * @Description: 在验证会话中的ticket有效之后,
    * @Date: 下午12:05 18-6-8
    */
    public UserModel getUserModelByTticket(String type, String ticket){
        UserModel userModel = null;
        try{
            userModel = common.getUserModelByTicket(type, ticket);
        }catch (Exception e){

        }
        return userModel;
    }

    public UserModel getUserModelByToken(String type, String token){
        return getUserModelByTticket(type, token);
    }

}
