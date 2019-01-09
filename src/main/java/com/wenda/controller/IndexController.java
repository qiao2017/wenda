package com.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月7日 下午11:33:04
 * @copyright qiao
 */
@Controller
public class IndexController {
    @RequestMapping(path= {"/", "/home"})
    public String index() {
        return "index";
    }
}
