package com.github.idonneedname.jmcomfessionwall_backend.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    public String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 关键配置：将物理路径映射为URL路径
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir);
    }
}