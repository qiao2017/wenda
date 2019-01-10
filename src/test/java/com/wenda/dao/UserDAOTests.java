package com.wenda.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wenda.entity.Question;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午8:26:13
 * @copyright qiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTests {
    @Autowired
    QuestionDAO questionDAO;
    @Test
    public void test() {
/*        for(int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setContent(String.format("test content test content %d", i));
            question.setTitle(String.format("title %d", i));
            question.setUserId(i+1);
            question.setCreatedDate(new Date(new Date().getTime() + 1000 * 3600 * i));
            questionDAO.addQuestion(question);
        }*/
        List<Question> questions = 
        questionDAO.selectLatestQuestions(0, 0, 5);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i).getTitle());
        }
    }
}
