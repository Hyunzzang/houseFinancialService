package com.hyunzzang.financial.house.infrastructure;

import com.hyunzzang.financial.house.application.interceptor.AccountInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private AccountInterceptor accountInterceptor;

    @Autowired
    public WebMvcConfig(AccountInterceptor accountInterceptor) {
        this.accountInterceptor = accountInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accountInterceptor)
                .addPathPatterns("/api/**/")
                .excludePathPatterns("/api/account/signup", "/api/account/signin");
    }
}
