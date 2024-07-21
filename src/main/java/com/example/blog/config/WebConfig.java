package com.example.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){

        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")){
            registry.addResourceHandler("/boardImage/**").addResourceLocations("file:///C:/blogImage/");
        }else{
            registry.addResourceHandler("/boardImage/**").addResourceLocations("file:/Users/Shared/blogImage/");
        }
    }
}
