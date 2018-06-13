package com.ackerman.interceptor;

import com.ackerman.UserModel;
import com.ackerman.service.SSOService;
import com.ackerman.utils.LocalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午7:41 18-6-7
 */
@Component
public class SSOInterceptor implements HandlerInterceptor{
    private static Logger logger = LoggerFactory.getLogger(SSOInterceptor.class);

    //只有登录的用户及其系统有全局会话
    private static final String GLOBAL_TICKET = "celtics_ticket";   //全局
    private static final String LOCAL_TICKET = "celtics_ticket_sport";    //局部
    private static final String CELTICS_TOKEN = "celtics_token";    //免登陆操作

    @Autowired
    private SSOService ssoService;

    @Autowired
    private LocalInfo localInfo;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            //1.　判断是否有局部会话
            HttpSession httpSession = request.getSession();
            String local = (String)httpSession.getAttribute(LOCAL_TICKET);
            logger.info("local: " + local);
            if(local != null && ssoService.verifyTicketOrUpdate(LOCAL_TICKET, local)) {
                UserModel userModel = ssoService.getUserModelByTticket(LOCAL_TICKET, local);
                localInfo.setAttribute("userModel", userModel);
                return true;
            }

            //登陆的时候, 一定会有本系统的局部会话 --> 也会有本系统的全局会话

            //2. 判断是否有全局会话, 如果存在, 创建局部会话
            String global = (String)httpSession.getAttribute(GLOBAL_TICKET);
            logger.info("global: " + global);
            if(global != null && ssoService.verifyTicketOrUpdate(GLOBAL_TICKET, global)){
                UserModel userModel = ssoService.getUserModelByTticket(GLOBAL_TICKET, global);
                ssoService.createLocalSession(request, LOCAL_TICKET, userModel);
                return true;
            }

            //3. 判断是否有免登陆系统
            String token = null;
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals(CELTICS_TOKEN)){
                    token = cookie.getValue();
                }
            }
            if(token != null && ssoService.verifyToken(CELTICS_TOKEN, token)){
                UserModel userModel = ssoService.getUserModelByToken(CELTICS_TOKEN, token);
                ssoService.createGlobalSession(GLOBAL_TICKET, userModel);
                ssoService.createLocalSession(request, LOCAL_TICKET, userModel);

                return true;
            }

//            HttpSession session = request.getSession();
//            String localTicket = (String) session.getAttribute(LOCAL_TICKET);
//            //会话中已经有了局部会话　sport系统的ticket, 此时可以单点登陆
//            if(localTicket != null && ssoService.verifyTicketAndUpdate(LOCAL_TICKET, localTicket)) {
//                //TODO 验证ticket的有效性, 并且设置threadlocal
//                int id = ssoService.getUserByTicket(LOCAL_TICKET, localTicket);
//                User user = new User();
//                user.setId(id);
//                user.setUsername(LOCAL_TICKET);
//                user.setPassword(localTicket);
//
//                return true;
//            }
//            // TODO 是否有全局会话
//            // TODO 验证global的有效性（包括验证用户的登录）
//            // TODO 并且创建本系统的局部会话
//
//            String global = (String)session.getAttribute(GLOBAL_TICKET);
//            if(global != null && ssoService.isLocalSession(GLOBAL_TICKET, global)){
//                int id = ssoService.getUserByTicket(LOCAL_TICKET, localTicket);
//                User user = new User();
//                user.setId(id);
//                user.setUsername(LOCAL_TICKET);
//                user.setPassword(localTicket);
//
//                int sessionTime = 60 * 60 * 2;
//
//                String local = ssoService.createTicket();
//                session.setAttribute(LOCAL_TICKET, local);
//                ssoService.setTicketExpireTime(LOCAL_TICKET, local, id, sessionTime);
//
//                session.setMaxInactiveInterval(sessionTime);
//
//                return true;
//            }
//
//
//            //查看是否有免登陆的Cookie
//            String token = null;
//            for(Cookie cookie : request.getCookies()){
//                if(cookie.getName().equals(CELTICS_TOKEN)){
//                    token = cookie.getValue();
//                    break;
//                }
//            }
//            //一旦cookie为null,　即此时需要手动登陆了
//            if(token == null)
//                return true;
//
//            //cookie不为null, 从cookie中读取用户保存的信息
//            int id = ssoService.getUserByTicket(CELTICS_TOKEN, token);
//            User user = new User();
//            user.setId(id/2);
//            user.setUsername("ljj");
//            user.setPassword("ackerman");
//
//            //登陆之后, 创建全局会话
//            int sessionTime = 60 * 60 * 2;  //2小时后失效
//            String globalTicket = ssoService.createTicket();
//            session.setAttribute(GLOBAL_TICKET, globalTicket);
//            ssoService.setTicketExpireTime(GLOBAL_TICKET, globalTicket, id, sessionTime);
//
//            //接着创建局部会话
//            String local = ssoService.createTicket();
//            session.setAttribute(LOCAL_TICKET, local);
//            ssoService.setTicketExpireTime(LOCAL_TICKET, local, id, sessionTime);
//
//            session.setMaxInactiveInterval(sessionTime);

        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        UserModel userModel = (UserModel) localInfo.getAttribute("userModel");
        String global = (String) localInfo.getAttribute("global");

        if(userModel != null)
            modelAndView.addObject("user", userModel);

        if(global != null){
            logger.info("my global: " + global);
            modelAndView.addObject("global", global);
        }

        HttpSession session = httpServletRequest.getSession();
        String g = (String)session.getAttribute(GLOBAL_TICKET);
        logger.info("your global_1: " + g);


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        localInfo.remove();
    }
}
