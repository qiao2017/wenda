package com.wenda.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenda.async.EventModel;
import com.wenda.async.EventProducer;
import com.wenda.async.EventType;
import com.wenda.entity.Comment;
import com.wenda.entity.EntityType;
import com.wenda.entity.HostHolder;
import com.wenda.entity.User;
import com.wenda.service.CommentService;
import com.wenda.service.LikeService;
import com.wenda.util.Util;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月27日 下午8:45:30
 * @copyright qiao
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    /**
     * 赞同，取消赞同
     */
    @RequestMapping(path = {"/comment/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") Integer commentId) {
        if (hostHolder.getUser() == null) {
            return Util.getJSONString(999);
        }
        
        User currentUser = hostHolder.getUser();
        if(likeService.getLikedStatus(currentUser.getId(), EntityType.ENTITY_COMMENT, commentId) != 1) {
            //站内信通知
            Comment comment = commentService.getCommentById(commentId);
            eventProducer.fireEvent(new EventModel(EventType.LIKE)
                    .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                    .setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                    .setExt("questionId", String.valueOf(comment.getEntityId())));
            System.out.println("发送1");
        }
        long likeCount = likeService.like(currentUser.getId(), EntityType.ENTITY_COMMENT, commentId);
        Map<String, Object> resData = new HashMap<>();
        resData.put("likeStatus", likeService.getLikedStatus(currentUser.getId(), EntityType.ENTITY_COMMENT, commentId));
        resData.put("likeCount", likeCount);
        return Util.getJSONString(0, resData);
    }

    @RequestMapping(path = {"/comment/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") Integer commentId) {
        if (hostHolder.getUser() == null) {
            return Util.getJSONString(999);
        }
        User currentUser = hostHolder.getUser();
        long likeCount = likeService.disLike(currentUser.getId(), EntityType.ENTITY_COMMENT, commentId);
        Map<String, Object> resData = new HashMap<>();
        resData.put("likeStatus", likeService.getLikedStatus(currentUser.getId(), EntityType.ENTITY_COMMENT, commentId));
        resData.put("likeCount", likeCount);
        return Util.getJSONString(0, resData);
    }
}
