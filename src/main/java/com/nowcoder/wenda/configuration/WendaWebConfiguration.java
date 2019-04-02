package com.nowcoder.wenda.configuration;

import com.nowcoder.wenda.interceptor.LoginRequiredInterceptor;
import com.nowcoder.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 在系统初始化的时候加入我们自己的拦截器，把拦截器注册到链路上
 * 课程中的WebMvcConfigurerAdapter已过时，这里我们自己实现WebMvcConfigurer接口
 * 参考：https://blog.csdn.net/pengdandezhi/article/details/81182701
 */
@Component
public class WendaWebConfiguration implements WebMvcConfigurer {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Resource
    LoginRequiredInterceptor loginRequiredInterceptor;

    /**
     * 注意注册拦截器是有先后顺序的！
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        //.addPathPatterns("/user/*")表示当访问用户页面时需要走这个拦截器
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
    }
}
