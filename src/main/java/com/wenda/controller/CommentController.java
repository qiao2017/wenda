package com.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wenda.entity.EntityType;
import com.wenda.service.CommentService;
import com.wenda.service.QuestionService;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午3:58:00
 * @copyright qiao
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired 
    CommentService commentService;
    @Autowired 
    QuestionService questionService;
    @RequestMapping(path = "/addComment", method = {RequestMethod.POST})
    public String addComment(@RequestParam("content") String content,
            @RequestParam("questionId") int questionId) {
        try {
            commentService.addComment(content, questionId, EntityType.ENTITY_QUESTION);
            int commentCount = commentService.getCommentCount(questionId, EntityType.ENTITY_QUESTION);
            questionService.updateCommentCount(questionId, commentCount);
        }catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}
