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
import org.springframework.web.bind.annotation.RequestParam;

import com.wenda.entity.Comment;
import com.wenda.entity.EntityType;
import com.wenda.entity.HostHolder;
import com.wenda.entity.Question;
import com.wenda.entity.User;
import com.wenda.entity.ViewObject;
import com.wenda.service.CommentService;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月21日 下午7:41:04
 * @copyright qiao
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    
    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    public String addQuestion(@RequestParam("title") String title,
            @RequestParam("content") String content) {
        try {
            questionService.addQuestion(title, content);
            User currentUser = hostHolder.getUser();
            return "redirect:/user/" + currentUser.getId();
        }catch (Exception e) {
            logger.error("增加题目失败！" + e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments", vos);
        return "detail";
    }

}
