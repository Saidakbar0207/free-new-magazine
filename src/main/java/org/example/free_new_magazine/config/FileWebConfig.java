package org.example.free_new_magazine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileWebConfig implements WebMvcConfigurer {
    @Value("${storage.root}")
    private String root;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:" + (root.endsWith("/") ? root : root + "/");
        registry.addResourceHandler("/files/**")
                .addResourceLocations(location);
    }
}
