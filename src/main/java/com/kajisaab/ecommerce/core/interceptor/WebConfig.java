package com.kajisaab.ecommerce.core.interceptor;

import com.kajisaab.ecommerce.core.jwt.CustomUserDetailsService;
import com.kajisaab.ecommerce.core.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Its purpose is to configure Spring MVC settings,
 * particularly to register an interceptor for intercepting incoming HTTP requests.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public WebConfig(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    };

    /**
     * Register any interceptor as per needed.
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthHandlerInterceptor());
    }
}
