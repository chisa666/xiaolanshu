package com.quanxiaoha.framework.biz.context.config;

import com.quanxiaoha.framework.biz.context.filter.HeaderUserId2ContextFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/** Registers the shared user-id context filter for servlet applications. */
@AutoConfiguration
public class ContextAutoConfiguration {
    @Bean
    public FilterRegistrationBean<HeaderUserId2ContextFilter> userIdContextFilterRegistration() {
        FilterRegistrationBean<HeaderUserId2ContextFilter> registration =
                new FilterRegistrationBean<>(new HeaderUserId2ContextFilter());
        registration.setOrder(-100);
        return registration;
    }
}