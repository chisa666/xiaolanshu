package com.quanxiaoha.xiaolanshu.comment.biz;

import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("com.quanxiaoha.xiaolanshu.comment.biz.domain.mapper")
public class xiaolanshuCommentBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuCommentBizApplication.class, args);
    }

}

