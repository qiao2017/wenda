package com.wenda.service;

import java.util.Random;

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
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Test
    public void test() {
        Random rand = new Random();
        for(int i = 12; i < 30; i++) {
            userService.register(String.valueOf(i), String.valueOf(i));
            followService.follow(i, EntityType.ENTITY_USER, i - (rand.nextInt(5) + 1));
            followService.follow(i, EntityType.ENTITY_USER, i - (rand.nextInt(5) + 5));
        }
    }
}
