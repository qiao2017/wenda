package com.wenda.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenda.entity.Question;
import com.wenda.entity.ViewObject;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月7日 下午11:33:04
 * @copyright qiao
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    
    @RequestMapping(path= {"/", "/index"}, 
                    method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("viewObjects", getQuestions(0, 0, 10));
        return "index";
    }
    
    @RequestMapping(path= {"/user/{userId}"})
    public String userIndex(Model model,
                    @PathVariable("userId") int userId) {
        model.addAttribute("viewObjects", getQuestions(userId, 0, 10));
        return "index";
    }
    
    private List<ViewObject> getQuestions(int userId, int offset, int limit){
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> viewObjects = new ArrayList<>();
        for(Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUser(question.getUserId()));
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }
}
