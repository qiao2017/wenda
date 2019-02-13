package com.wenda.service;
/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午7:30:18
 * @copyright qiao
 */

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.wenda.dao.QuestionDAO;
import com.wenda.entity.HostHolder;
import com.wenda.entity.Question;
import com.wenda.util.Util;
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    SensitiveService sensitiveService;
   
    public List<Question> getLatestQuestions(int userId, int offset, int limit){
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
    
    //添加问题，返回questionId
    public int addQuestion(String title, String content) {
        //敏感词过滤 
        
        Question question = new Question();
//        question.setContent(HtmlUtils.htmlEscape(content));
//        question.setTitle(HtmlUtils.htmlEscape(title));
        question.setTitle(sensitiveService.filter(title));
        question.setCommentCount(0);
        question.setContent(sensitiveService.filter(content));
        int userId = Util.ANONYMOUS;
        if(hostHolder.getUser() != null) {
            userId = hostHolder.getUser().getId();
        }
        question.setUserId(userId);
        question.setCreatedDate(new Date());
        return questionDAO.addQuestion(question);
    }
    
    public Question getById(int qid) {
        return questionDAO.selectById(qid);
    }
    
    public boolean updateCommentCount(int questionId, int count) {
        return questionDAO.updateCommentCount(questionId, count) > 0;
    }
}
