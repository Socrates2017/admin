package com.zrzhen.admin.config;

import com.zrzhen.admin.handler.AdminInterceptor;
import com.zrzhen.admin.module.ueditor.UeditorUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    //将jar文件下的对应静态资源文件路径对应到磁盘的路径(根据个人的情况修改"file:static/"的static的值
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/static/**").addResourceLocations("file:" + UeditorUtil.getUploadDir());
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(adminInterceptor());
        registration.addPathPatterns("/admin/**");
        registration.addPathPatterns("/api/v1/admin/**");
        registration.addPathPatterns("/api/v1/file/**");

        List<String> excludePathPatterns = new ArrayList<>();
        excludePathPatterns.add("/admin");
        excludePathPatterns.add("/api/v1/admin/user/**");

        registration.excludePathPatterns(excludePathPatterns);


        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }


}
