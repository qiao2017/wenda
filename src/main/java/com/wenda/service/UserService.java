package com.wenda.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;
import com.wenda.dao.LoginTicketDAO;
import com.wenda.dao.UserDAO;
import com.wenda.entity.LoginTicket;
import com.wenda.entity.User;
import com.wenda.util.Util;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午8:51:08
 * @copyright qiao
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LoginTicketDAO loginTicketDAO;
    
    public User getUser(int id) {
        return userDAO.selectById(id);
    }
    
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmptyOrWhitespaceOnly(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }
        
        user = new User();
        user.setName(username);
        String salt = UUID.randomUUID().toString()
                        .replaceAll("-", "").substring(0, 7);
        user.setSalt(salt);
        String headUrl = String.format("http://images.nowcoder.com/head/%dt.png", 
                                new Random().nextInt(1000));
        user.setHeadUrl(headUrl);
        user.setPassword(Util.md5(password + salt));
        user.setCreateDate(new Date());
        userDAO.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }
    
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmptyOrWhitespaceOnly(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }
        
        if(!Util.md5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }else {
            String ticket = addLoginTicket(user.getId());
            map.put("ticket", ticket);
            return map;
        }
    }
    
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket);
    }
    
    public String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24 * 7);
        loginTicket.setExpired(date);
        String ticket = UUID.randomUUID().toString().replaceAll("-", "");
        loginTicket.setTicket(ticket);
        loginTicket.setStatus(LoginTicket.VALID);
        loginTicketDAO.adTicket(loginTicket);
        return ticket;
    }

    public User selectByName(String toName) {
        return userDAO.selectByName(toName);
    }
}
