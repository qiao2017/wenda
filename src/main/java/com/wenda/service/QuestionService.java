package com.wenda.service;
/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午7:30:18
 * @copyright qiao
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenda.dao.QuestionDAO;
import com.wenda.entity.Question;
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    
    public List<Question> getLatestQuestions(int userId, int offset, int limit){
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
}
