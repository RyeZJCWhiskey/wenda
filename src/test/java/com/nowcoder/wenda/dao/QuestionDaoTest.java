package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.WendaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WendaApplication.class})// classes属性指定启动类
public class QuestionDaoTest {
    @Autowired
    QuestionDao questionDao;

    @Test
    public void test(){
        System.out.println(questionDao.selectLatestQuestions(0,0,10));
        questionDao.selectLatestQuestions(0,0,10);
    }

}