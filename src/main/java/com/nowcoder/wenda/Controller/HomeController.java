package com.nowcoder.wenda.Controller;

import com.nowcoder.wenda.model.Question;
import com.nowcoder.wenda.model.ViewObject;
import com.nowcoder.wenda.service.QuestionService;
import com.nowcoder.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Resource
    QuestionService questionService;

    @Resource
    UserService userService;

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 从0开始查询最近的十个question
     * @param model
     * @param pop
     * @return
     */
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }

    /**
     * 从传进来的userId开始查询最近的十个question
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 3));
        return "index";
    }
}
