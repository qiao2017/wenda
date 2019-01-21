package com.wenda.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wenda.dao.LoginTicketDAO;
import com.wenda.dao.UserDAO;
import com.wenda.entity.HostHolder;
import com.wenda.entity.LoginTicket;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月20日 下午8:03:24
 * @copyright qiao
 */
@Component
public class PassPortInterceptor implements HandlerInterceptor{
    @Autowired
    LoginTicketDAO loginTicketDAO;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserDAO userDAO;
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        hostHolder.clear();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ticket = null;
        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
               if(cookie.getName().equals("ticket")) {
                   ticket = cookie.getValue();
                   break;
               }
            }
        }
        if(ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if(loginTicket != null && loginTicket.getExpired().after(new Date())
                    && loginTicket.getStatus().equals(LoginTicket.VALID)) {
                hostHolder.setUser(userDAO.selectById(loginTicket.getUserId()));
            }
        }
        return true;
    }
    
}
