package com.wenda.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年2月13日 下午7:37:49
 * @copyright qiao
 */
@Service
public class MailService implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    @Value("${spring.mail.username}")
    private String senderMailAddress;
    
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    
    
    public void sendMail(Map<String, Object> valueMap, String file) {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置发件人邮箱
            helper.setFrom(senderMailAddress);
            // 设置收件人邮箱
            
            helper.setTo(valueMap.get("to").toString());
            // 设置邮件标题
            helper.setSubject(valueMap.get("title").toString());

            // 添加正文（使用thymeleaf模板）
            Context context = new Context();
            context.setVariables(valueMap);
            String content = this.templateEngine.process(file, context);
            helper.setText(content, true);

/*            // 添加附件
            if (valueMap.get("filePathList") != null) {
                String[] filePathList = (String[]) valueMap.get("filePathList");
                for(String filePath: filePathList) {
                    FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
                    String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                    helper.addAttachment(fileName, fileSystemResource);
                }
            }
*/
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送邮件失败！" + e.getMessage());
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}