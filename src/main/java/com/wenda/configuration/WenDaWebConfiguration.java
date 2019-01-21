package com.wenda.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wenda.interceptor.LoginRequiredInterceptor;
import com.wenda.interceptor.PassPortInterceptor;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月20日 下午8:23:58
 * @copyright qiao
 */
@Component
public class WenDaWebConfiguration implements WebMvcConfigurer{
    @Autowired
    PassPortInterceptor passPortInterceptor;
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passPortInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
