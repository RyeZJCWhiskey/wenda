package com.nowcoder.wenda.interceptor;

import com.nowcoder.wenda.dao.LoginTicketDao;
import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 这个拦截器与HostHolder配合，为了让所有的视图层页面都能访问到user这个用户变量
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Resource
    LoginTicketDao loginTicketDao;

    @Resource
    UserDao userDao;

    @Resource
    HostHolder hostHolder;

    @Override
    //请求开始之前先回调这个函数，返回时false后面的Controller方法将不再执行，即被拦截器拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0)
                return true;
            User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUsers(user);
        }
        return true;
    }

    @Override
    //Controller中的方法执行完后再来回调这个函数
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    //整个View都渲染完了再来回调这个函数，作用是可以清除一些数据
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
