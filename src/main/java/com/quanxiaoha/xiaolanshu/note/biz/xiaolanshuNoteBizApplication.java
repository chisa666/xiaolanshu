package com.quanxiaoha.xiaolanshu.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.dev33.satoken.reactor.spring.SaTokenContextRegister;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

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
@MapperScan("com.quanxiaoha.xiaolanshu.note.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.quanxiaoha.xiaolanshu")
@ComponentScan({"com.quanxiaoha.xiaolanshu.note.biz", "com.quanxiaoha.xiaolanshu.count.fallback"})
public class xiaolanshuNoteBizApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(xiaolanshuNoteBizApplication.class);
        application.setAdditionalProfiles("note");
        application.run(args);
    }

}

