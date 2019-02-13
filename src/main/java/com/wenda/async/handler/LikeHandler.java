package com.wenda.async.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wenda.async.EventHandler;
import com.wenda.async.EventModel;
import com.wenda.async.EventType;
import com.wenda.entity.Message;
import com.wenda.entity.User;
import com.wenda.service.MessageService;
import com.wenda.service.UserService;
import com.wenda.util.Util;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年2月13日 上午10:41:29
 * @copyright qiao
 */
@Component
public class LikeHandler implements EventHandler{

    @Autowired
    MessageService messageService;
    
    @Autowired
    UserService userService;
    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(Util.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreateDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "赞了你的评论/question/"
                + model.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        List<EventType> res = new ArrayList<>();
        res.add(EventType.LIKE);
        return res;
    }

}
