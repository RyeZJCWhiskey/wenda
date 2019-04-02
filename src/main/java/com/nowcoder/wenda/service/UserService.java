package com.nowcoder.wenda.service;

import com.nowcoder.wenda.dao.LoginTicketDao;
import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.User;
import com.nowcoder.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.String;
import javax.annotation.Resource;
import java.util.*;

@Service
public class UserService {
    @Resource
    UserDao userDao;

    @Resource
    LoginTicketDao loginTicketDao;

    public User selectByName(String name) {
        return userDao.selectByName(name);
    }

    public Map<String, String>register(String username, String password){
        Map<String, String> map = new HashMap<>();
        //StringUtils不属于Sun-JDK中的类,要在Pom中引入commons-lang
        //isBlank()判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        if(StringUtils.isBlank(username)) {
        map.put("msg","用戶名不能为空");
        return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if (user != null){
            map.put("msg","用户名已被注册");
            return map;
        }
        user = new User();
        user.setName(username);
        /*UUID 含义是通用唯一识别码 (Universally Unique Identifier)
        UUID 的目的，是让分布式系统中的所有元素，都能有唯一的辨识资讯，而不需要透过中央控制端来做辨识资讯的指定。
        如此一来，每个人都可以建立不与其它人冲突的 UUID。在这样的情况下，就不需考虑数据库建立时的名称重复问题。*/
        // 利用盐值字符串增加密码强度
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadURL(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);

        //刚注册好的新用户自动登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public User getUser(int id){
        return userDao.selectById(id);
    }

    public Map<String, String>login(String username, String password){
        Map<String, String> map = new HashMap<>();
        //StringUtils不属于Sun-JDK中的类,要在Pom中引入commons-lang
        //isBlank()判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        if(StringUtils.isBlank(username)) {
            map.put("msg","用戶名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if (user == null){
            map.put("msg","用户名不存在");
            return map;
        }
        if(!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId", user.getId()+"");
        return map;
    }

    private String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(now.getTime() + 3600*24*100);//有效时间是100天，这里单位是秒
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);//设置默认状态为0，表示有效
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }
}
