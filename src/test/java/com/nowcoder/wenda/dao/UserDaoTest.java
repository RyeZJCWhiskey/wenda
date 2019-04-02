package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.WendaApplication;
import com.nowcoder.wenda.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Random;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WendaApplication.class})// classes属性指定启动类
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void contextLoads(){
        Random random = new Random();
        //循环创建了10个User对象进行了操作,for循环用Postfix这么写：11.fori
        for (int i = 0; i < 11; i++) {
            User user = new User();
            user.setHeadURL(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt()));
            user.setName(String.format("USER%d",i));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);
            /*user.setPassword("123456");
            userDao.updatePassword(user);*/
        }
        //Assert.assertEquals("123456", userDao.selectById(1).getPassword());
    }

}