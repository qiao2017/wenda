package com.wenda.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wenda.dao.CommentDAO;
import com.wenda.entity.Comment;
import com.wenda.entity.EntityType;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午4:30:19
 * @copyright qiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentDAO commentDAO;
    @Test
    public void test() {
        String content = "测试品你概论测试品你概论测试品你概论测试品你概论";
        
//        commentService.addComment(content, 21, EntityType.ENTITY_QUESTION);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserId(21);
        commentDAO.addComment(comment);
    }
}
