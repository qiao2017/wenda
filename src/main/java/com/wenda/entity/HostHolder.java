package com.wenda.entity;

import org.springframework.stereotype.Component;

/**
 * @author 乔莹
 * @version TODO
 * @time  2018年12月16日 下午6:57:27
 * @copyright qiao
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();
    
    public User getUser() {
        return users.get();
    }
    
    public void setUser(User user) {
        users.set(user);
    }
    
    public void clear() {
        users.remove();
    }
}
