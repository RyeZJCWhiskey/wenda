package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.WendaApplication;
import com.nowcoder.wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WendaApplication.class)
//@Sql("/schema.sql")
public class InitDataBaseTest {

    @Autowired
    UserDao userDao;

    @Test
    public void initDataBaes(){
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            User user = new User();
            user.setHeadURL(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d",i));
            user.setSalt("");
            user.setPassword("");
            userDao.addUser(user);

        }
    }
}
