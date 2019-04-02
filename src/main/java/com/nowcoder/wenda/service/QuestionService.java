package com.nowcoder.wenda.service;

import com.nowcoder.wenda.dao.QuestionDao;
import com.nowcoder.wenda.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionService {
    @Resource
    QuestionDao questionDao;

    @Resource
    SensitiveService sensitiveService;

    public int addQuestion(Question question) {
        //html语句过滤,防止用户恶意提交含有html代码的文本
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public Question selectById(int id){
        return questionDao.selectById(id);
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    public int updateCommentCount(int id, int count) {
        return questionDao.updateCommentCount(id, count);
    }
}
