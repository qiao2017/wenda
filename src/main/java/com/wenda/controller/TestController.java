package com.wenda.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenda.service.MailService;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月9日 下午8:41:41
 * @copyright qiao
 */
@Controller
public class TestController {
    @Autowired
    MailService mailService;
    
    @RequestMapping(path= {"/test/", "/test/index", "/test/home"})
    @ResponseBody
    public String index() {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("to", new String[]{"2604678699@qq.com", "qiaoying_1@qq.com"});
        valueMap.put("title", "邮件标题");
        valueMap.put("content", "邮件内容");
        mailService.sendMail(valueMap, "mails/mail");
        return "Hello";
    }
    
/*    @RequestMapping(path= {"/test/{groupId}/{userId}"})
    @ResponseBody
    public String test(@PathVariable("userId") int userId) {
        return String.format("userId is %d", userId);
    }*/
    

    @RequestMapping(path = {"/queryStudentInfo"})
    public String queryStudentInfo(Model model) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> student = new HashMap<String, Object>(){{
            put("sid", "101");
            put("sname", "张三");
            put("sage", "20");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "语文,数学,英语");
                put("cscore", "93,95,98");
            }});
        }};
        resultList.add(student);
        student = new HashMap<String, Object>(){{
            put("sid", "102");
            put("sname", "李四");
            put("sage", "30");
            put("scourse", new HashMap<String, String>(){{
                put("cname", "物理,化学,生物");
                put("cscore", "92,93,97");
            }});
        }};
        resultList.add(student);
        model.addAttribute("resultList", resultList);
        System.out.println(resultList);
        return "test";
    }
}
