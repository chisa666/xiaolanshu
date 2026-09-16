package com.quanxiaoha.xiaolanshu.oss.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.dev33.satoken.reactor.spring.SaTokenContextRegister;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration;

@SpringBootApplication(exclude = {GatewayAutoConfiguration.class, GatewayClassPathWarningAutoConfiguration.class, SaTokenContextRegister.class},
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
public class xiaolanshuOssBizApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(xiaolanshuOssBizApplication.class);
        application.setAdditionalProfiles("oss");
        application.run(args);
    }

}

