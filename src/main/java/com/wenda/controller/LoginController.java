package com.wenda.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @RequestMapping(path= {"/register"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String register(@RequestParam("username") String username,
                            @RequestParam("password") String password) {
        Map<String, Object> map = userService.register(username, password);
        
        return "index";
    }

    
    @RequestMapping(path= {"/login"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String login(Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "rememberme", required = false, defaultValue = "false") Boolean rememberme) {
        try {
            Map<String, Object> map = userService.register(username, password);
            
            if(map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        }catch(Exception e) {
            logger.error("登录异常" + e.getMessage());
            return "redirect:/";
        }
        return "login";

    }

}
