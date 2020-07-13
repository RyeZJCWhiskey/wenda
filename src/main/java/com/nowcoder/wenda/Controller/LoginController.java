package com.nowcoder.wenda.Controller;

import com.nowcoder.wenda.async.EventModel;
import com.nowcoder.wenda.async.EventProducer;
import com.nowcoder.wenda.async.EventType;
import com.nowcoder.wenda.service.UserService;
//import com.sun.deploy.net.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    UserService userService;

    @Resource
    EventProducer eventProducer;

    //next参数是为了在某一未登录的页面进行操作时强制跳转到登录页面，用户登录后能自动跳回登录前浏览的页面
    //next的值是在拦截器LoginRequiredInterceptor的preHandle方法中赋值的
    @RequestMapping(path = {"/reg"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "next", required = false)String next,
                      @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme,
                      HttpServletResponse httpServletResponse) {
        try {
            Map<String, String> map = userService.register(username, password);
            if (!map.containsKey("msg")){
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                //如果用户勾选了记住我，则ticket这个cookie的存留时间为5天
                if (rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                httpServletResponse.addCookie(cookie);
                if (StringUtils.isNotBlank(next)){
                    return "redirect:" + next;
                }
                return "redirect:/";
            }
            else {
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.error("注册异常:" + e.getMessage());
            return "login";

        }
    }

    @RequestMapping(path = {"/reglogin"},method = {RequestMethod.GET})
    public String reg(Model model,
                      @RequestParam(value = "next", required = false)String next){
        model.addAttribute("next",next);
        return "login";
    }
    //next参数是为了在某一未登录的页面进行操作时强制跳转到登录页面，用户登录后能自动跳回登录前浏览的页面
    //next的值是在拦截器LoginRequiredInterceptor的preHandle方法中赋值的
    @RequestMapping(path = {"/login/"},method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rememberme",defaultValue = "false") boolean rememberme,
                        @RequestParam(value = "next", required = false)String next,
                        HttpServletResponse httpServletResponse){
        try {
            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                httpServletResponse.addCookie(cookie);

                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                        .setExt("username", username).setExt("email", "2417319177@qq.com")
                        .setActorId(Integer.parseInt(map.get("userId"))));

                if (StringUtils.isNotBlank(next)){
                    return "redirect:" + next;
                }
                return "redirect:/";
            }
            else {
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.error("登录异常:" + e.getMessage());
            return "login";

        }
    }

    @RequestMapping(path = {"/logout"},method = RequestMethod.GET)
    public String logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
