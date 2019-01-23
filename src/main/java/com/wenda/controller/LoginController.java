package com.wenda.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.cj.util.StringUtils;
import com.wenda.service.UserService;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月12日 下午7:52:45
 * @copyright qiao
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
//    @Autowired
    
    @RequestMapping(path = { "/login" }, method = { RequestMethod.POST, RequestMethod.GET })
    public String register(Model model, @RequestParam("username") String username,
            @RequestParam("password") String password, HttpServletResponse response,
            @RequestParam(value = "rememberme", required = false, defaultValue = "false") boolean rememberme,
            @RequestParam(value = "next", required = false) String next) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {// 登录成功
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                if(!StringUtils.isEmptyOrWhitespaceOnly(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {// 登录失败
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return "redirect:/";
        }

    }

    
    @RequestMapping(path= {"/register"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String login(Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            @RequestParam(value = "rememberme", required = false, defaultValue = "false") boolean rememberme) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if(map.containsKey("ticket")) {//登录成功
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else {//登录失败
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        }catch(Exception e) {
            logger.error("登录异常" + e.getMessage());
            return "redirect:/";
        }
    }

    @RequestMapping(path= {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
    
    @RequestMapping(path= {"/reglogin"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String reLogin(Model model,
            @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }
}
