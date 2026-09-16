package com.quanxiaoha.xiaolanshu.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.dev33.satoken.reactor.spring.SaTokenContextRegister;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.quanxiaoha.xiaolanshu.search.biz",
        exclude = {GatewayAutoConfiguration.class, GatewayClassPathWarningAutoConfiguration.class, SaTokenContextRegister.class},
        excludeName = {
        "org.springframework.cloud.gateway.config.GatewayRedisAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayResilience4JCircuitBreakerAutoConfiguration",
        "org.springframework.cloud.gateway.config.SimpleUrlHandlerMappingGlobalCorsAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayReactiveOAuth2AutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayMetricsAutoConfiguration",
        "org.springframework.cloud.gateway.config.LocalResponseCacheAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayNoLoadBalancerClientAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration"
})
@EnableScheduling
@MapperScan("com.quanxiaoha.xiaolanshu.search.biz.domain.mapper")
public class xiaolanshuSearchApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(xiaolanshuSearchApplication.class);
        application.setAdditionalProfiles("search");
        application.run(args);
    }

}

