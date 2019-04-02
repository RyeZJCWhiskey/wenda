package com.nowcoder.wenda.Controller;

import com.nowcoder.wenda.model.Comment;
import com.nowcoder.wenda.model.EntityType;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.service.CommentService;
import com.nowcoder.wenda.service.QuestionService;
import com.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentControler {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = {"/addComment"}, method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId")int questionId,@RequestParam("content")String content){
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() != null){
                comment.setUserId(hostHolder.getUser().getId());
            }else {
                //给一个匿名用户
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
                //return "redirect:/reglogin";//也可以未登录直接返回登录页面
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            commentService.addComment(comment);
            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);
        }catch (Exception e){
            logger.error("增加评论失败" + e.getMessage());
        }

        return "redirect:/question/" + String.valueOf(questionId);
    }

}
