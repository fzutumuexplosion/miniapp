package com.itwu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Value("/human/img/")
    private String imagePath;

    @Value("/img/")
    private String upload;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE","OPTION")
                .maxAge(3600);
    }


    //token拦截
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**");
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler()里配置需要映射的文件夹
        //addResourceLocations()配置文件夹在系统中的路径，使用绝对路径，格式为“file:你的路径”
        //registry.addResourceHandler(upload + "**").addResourceLocations("file:C:\\jiuqi\\upload\\");
        registry.addResourceHandler(upload + "**").addResourceLocations("file:" + imagePath);
    }


}
