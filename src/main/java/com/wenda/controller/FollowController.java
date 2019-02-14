package com.wenda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenda.async.EventModel;
import com.wenda.async.EventProducer;
import com.wenda.async.EventType;
import com.wenda.entity.EntityType;
import com.wenda.entity.HostHolder;
import com.wenda.entity.Question;
import com.wenda.entity.User;
import com.wenda.entity.ViewObject;
import com.wenda.service.CommentService;
import com.wenda.service.FollowService;
import com.wenda.service.QuestionService;
import com.wenda.service.UserService;
import com.wenda.util.Util;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月27日 下午8:45:30
 * @copyright qiao
 */
@Controller
public class FollowController {
    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    /**
     * 赞同，取消赞同
     */
    @RequestMapping(path = {"/unFollowUser"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String unfollow(@RequestParam("userId") Integer userId) {
        if (hostHolder.getUser() == null) {
            return Util.getJSONString(999);
        }
        
        User currentUser = hostHolder.getUser();
        
        boolean res = followService.unFollow(currentUser.getId(), EntityType.ENTITY_USER, userId);
        
        EventModel eventModel = new EventModel(EventType.FOLLOW)
                .setActorId(currentUser.getId())
                .setEntityId(EntityType.ENTITY_USER)
                .setEntityOwnerId(userId)
                .setEntityType(EntityType.ENTITY_USER);
        eventProducer.fireEvent(eventModel);
        long followCount = followService.getFolloweeCount(currentUser.getId(), EntityType.ENTITY_USER);
        return Util.getJSONString(res ? 0 : 1, String.valueOf(followCount));
    }

    @RequestMapping(path = {"/followUser"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String follow(@RequestParam("userId") Integer userId) {
        if (hostHolder.getUser() == null) {
            return Util.getJSONString(999);
        }

        boolean ret = followService.unFollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(userId)
                .setEntityType(EntityType.ENTITY_USER).setEntityOwnerId(userId));

        // 返回关注的人数
        return Util.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
    }
    
    @RequestMapping(path = {"/followQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId) {
        if (hostHolder.getUser() == null) {
            return Util.getJSONString(999);
        }

        Question q = questionService.getById(questionId);
        if (q == null) {
            return Util.getJSONString(1, "问题不存在");
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(questionId)
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityOwnerId(q.getUserId()));

        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));
        return Util.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = {"/unfollowQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId) {
        if (hostHolder.getUser() == null) {
            return Util.getJSONString(999);
        }

        Question q = questionService.getById(questionId);
        if (q == null) {
            return Util.getJSONString(1, "问题不存在");
        }

        boolean ret = followService.unFollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(questionId)
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityOwnerId(q.getUserId()));

        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));
        return Util.getJSONString(ret ? 0 : 1, info);
    }
    
    
    @RequestMapping(path = {"/user/{uid}/followers"}, method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("uid") int userId) {
        List<Integer> followerIds = followService.getFollowers(EntityType.ENTITY_USER, userId, 0, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followerIds));
        } else {
            model.addAttribute("followers", getUsersInfo(0, followerIds));
        }
        model.addAttribute("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followers";
    }

    @RequestMapping(path = {"/user/{uid}/followees"}, method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("uid") int userId) {
        List<Integer> followeeIds = followService.getFollowees(userId, EntityType.ENTITY_USER, 0, 10);
        System.out.println(followeeIds);
        System.out.println(followeeIds.size());
        if (hostHolder.getUser() != null) {
            model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followeeIds));
        } else {
            model.addAttribute("followees", getUsersInfo(0, followeeIds));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        System.out.println("followeeCount" + followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followees";
    }

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> userInfos = new ArrayList<ViewObject>();
        for (Integer uid : userIds) {
            User user = userService.getUser(uid);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("commentCount", commentService.getUserCommentCount(uid));
            vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, uid));
            vo.set("followeeCount", followService.getFolloweeCount(uid, EntityType.ENTITY_USER));
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, EntityType.ENTITY_USER, uid));
            } else {
                vo.set("followed", false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }
}
