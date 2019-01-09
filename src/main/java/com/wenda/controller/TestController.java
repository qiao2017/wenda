package com.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月9日 下午8:41:41
 * @copyright qiao
 */
@Controller
public class TestController {
    @RequestMapping(path= {"/test/", "/test/index", "/test/home"})
    @ResponseBody
    public String index() {
        return "Hello";
    }
    
    @RequestMapping(path= {"/test/{groupId}/{userId}"})
    @ResponseBody
    public String test(@PathVariable("userId") int userId) {
        return String.format("userId is %d", userId);
    }
}
