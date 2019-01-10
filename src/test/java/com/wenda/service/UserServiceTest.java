package com.wenda.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wenda.dao.UserDAO;
import com.wenda.entity.Question;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午9:22:16
 * @copyright qiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    public void test() {
        for(int i = 0; i < 10; i++) {
            userService.register(String.valueOf(i + 1), String.valueOf(i + 1));
        }
        
    }
}
