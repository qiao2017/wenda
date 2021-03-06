package com.wenda.controller;
/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午6:55:06
 * @copyright qiao
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenda.entity.HostHolder;
import com.wenda.entity.Message;
import com.wenda.entity.User;
import com.wenda.entity.ViewObject;
import com.wenda.service.MessageService;
import com.wenda.service.UserService;
import com.wenda.util.Util;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    
    @RequestMapping(path= {"/msg/addMessage"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
            @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return Util.getJSONString(999, "未登录");
            }

            User user = userService.selectByName(toName.trim());
            if (user == null) {
                return Util.getJSONString(1, "用户不存在");
            }
            
            Message message = new Message();
            message.setCreateDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content.trim());
            messageService.addMessage(message);
            return Util.getJSONString(0);

        } catch (Exception e) {
            logger.error("发送消息失败" + e.getMessage());
            return Util.getJSONString(2, "发信失败");
        }
    }
    
    
    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model) {
        try {
            if(hostHolder.getUser() == null) {
                return "redirect:/reglogin";
            }
            int currentUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<ViewObject>();
            List<Message> conversationList = messageService.getConversationList(currentUserId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                int targetId = msg.getFromId() == currentUserId ? msg.getToId() : msg.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user", user);
                int unReadCount = messageService.getConVersationUnReadCount(currentUserId, msg.getConversationId());
                vo.set("unread", unReadCount > 99 ? "99+" : unReadCount);
                conversations.add(vo);
            }
                model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @Param("conversationId") String conversationId) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情消息失败" + e.getMessage());
        }
        return "letterDetail";
    }
}
