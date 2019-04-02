package com.nowcoder.wenda.interceptor;

import com.nowcoder.wenda.dao.LoginTicketDao;
import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.HostHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当访问某些特定页面时这个拦截器让用户强制登录，没有登录只要服务器一收到http请求就会自动跳转到登录页面
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Resource
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hostHolder.getUser() == null){
            //请求重定向
            response.sendRedirect("/reglogin?next=" + request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
