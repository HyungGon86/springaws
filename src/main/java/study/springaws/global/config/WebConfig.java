package study.springaws.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/summernoteImage/**")
                .addResourceLocations("file:///home/ec2-user/deploy/upload/attachedFiles/");

        registry.addResourceHandler("/thumbnail/**")
                .addResourceLocations("file:///home/ec2-user/deploy/upload/thumbnail/");
    }
}
