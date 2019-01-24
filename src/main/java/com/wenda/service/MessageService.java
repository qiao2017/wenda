package com.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenda.dao.MessageDAO;
import com.wenda.entity.Message;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午6:49:09
 * @copyright qiao
 */
@Service 
public class MessageService {
    @Autowired
    MessageDAO messageDAO;
    @Autowired 
    SensitiveService sensitiveService;
    
    public int addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }
    
    public List<Message> getConversationDetail(String conversationId, int offset, int limit){
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }
    
    public List<Message> getConversationList(int userId, int offset, int limit){
        return messageDAO.getConversationList(userId, offset, limit);
    }
    
    public int getConVersationUnReadCount(int userId, String conversationId) {
        return messageDAO.getConVersationUnReadCount(userId, conversationId);
    }
}
