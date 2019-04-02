package com.nowcoder.wenda.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;


//@Controller //后台服务器入口层，下面这个类是首页controller
public class IndexController {

//    //@RequestMapping("/") //访问斜杠 localhost:8080/ 会被这个controller方法处理
//    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET) //指定了两个路径映射都用这个函数来处理访问
//    @ResponseBody //返回的是字符串而不是模板
//    public String index(HttpSession httpSession){
//        return "Hellow Nowcoder" + httpSession.getAttribute("msg");
//    }


    /**
     * 访问URL：127.0.0.1:8080/profile/user/123?type=2&key=z
     * 把URL路径中的参数通过RequestMapping中的{}和@PathVariable解析到Controller方法的变量中
     * 请求中的参数也就是？后面的参数通过@RequestParam解析到Controller方法的变量中,required = false表示这个参数可以不传
     * @param groupId
     * @param userId
     * @param type
     * @param key
     * @return
     */
//    @RequestMapping(path = {"/profile/{groupId}/{userId}"}) //指定了两个路径映射都用这个函数来处理访问
//    @ResponseBody //返回的是字符串而不是模板
//    public String profile(@PathVariable("groupId") String groupId,
//                          @PathVariable("userId") int userId,
//                          @RequestParam(value = "type",defaultValue = "1") int type,
//                          @RequestParam(value = "key", required = false) String key){
//        return String.format("Profile Page of %s / %d, t:%d, key:%s", groupId, userId);
//    }
//
//    //@RequestMapping("/") //访问斜杠 localhost:8080/ 会被这个controller方法处理
//    //*.vm 后缀的文件，是velocity的文件。velocity是基于java的一种页面模板引擎，
//    //可以用文本编译器打开，能够看到他只是一些类似html的语句和一种叫VLT的语句构成的。
//
//    /**
//     * *.vm 后缀的文件，是velocity的文件。velocity是基于java的一种页面模板引擎，
//     * 可以用文本编译器打开，能够看到他只是一些类似html的语句和一种叫VLT的语句构成的。
//     * @return
//     */
//    @RequestMapping(path = {"/vm"}, method = RequestMethod.GET)
//    public String template(Model model){
//        model.addAttribute("name", "hahahahah");
//        model.addAttribute("currentDate", new Date());
//        return "home";
//    }
//
//    @RequestMapping(path = {"/redirect/{code}"}, method = RequestMethod.GET)
//    public String redirect(@PathVariable("code") int code, HttpSession httpSession){
//        httpSession.setAttribute("msg", "hahahjump from redirect");
//        return "redirect:/";
//    }




}

