package com.wenda.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.wenda.dao.CommentDAO;
import com.wenda.entity.Comment;
import com.wenda.entity.HostHolder;
import com.wenda.util.Util;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午3:42:38
 * @copyright qiao
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    SensitiveService sensitiveService;
    @Autowired
    HostHolder hostHolder;
    
    public List<Comment> getCommentsByEntity(int entityId, int entityType){
        return commentDAO.selectCommentByEntity(entityId, entityType, 0, 10);
    }

    public int addComment(String content, int entityId, int entityType) {
        Comment comment = new Comment();
        comment.setStatus(Comment.VALID);
        comment.setContent(sensitiveService.filter(HtmlUtils.htmlEscape(content)));
        comment.setCreateDate(new Date());
        if(hostHolder.getUser() != null) {
            comment.setUserId(hostHolder.getUser().getId());
        }else {
            comment.setUserId(Util.ANONYMOUS);
        }
        comment.setEntityId(entityId);
        comment.setEntityType(entityType);
        
        return commentDAO.addComment(comment) > 0 ? comment.getId() : 0;
    }
    
    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }
    
    public boolean deleteComment(int id) {
        return commentDAO.updateStatus(id, Comment.INVALID) > 0;
    }

    public Comment getCommentById(Integer commentId) {
        return commentDAO.getCommentById(commentId);
    }
}
