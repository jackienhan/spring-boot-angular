package com.bezkoder.configure;

import com.bezkoder.configure.interceptor.LogInterceptor;
import com.bezkoder.configure.interceptor.ViewInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor());
        registry.addInterceptor(viewInterceptor()).addPathPatterns("/api/auth/signin");
    }

    @Bean
    LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }
    @Bean
    ViewInterceptor viewInterceptor() {
        return new ViewInterceptor();
    }
}
