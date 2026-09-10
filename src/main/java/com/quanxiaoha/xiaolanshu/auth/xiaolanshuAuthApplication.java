package com.quanxiaoha.xiaolanshu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration;
import cn.dev33.satoken.reactor.spring.SaTokenContextRegister;
import com.alibaba.cloud.sentinel.SentinelWebAutoConfiguration;

@SpringBootApplication(exclude = {
        GatewayAutoConfiguration.class,
        GatewayClassPathWarningAutoConfiguration.class,
        SaTokenContextRegister.class,
        SentinelWebAutoConfiguration.class,
        CassandraAutoConfiguration.class,
        CassandraDataAutoConfiguration.class,
        CassandraRepositoriesAutoConfiguration.class
}, excludeName = {
        "org.springframework.cloud.gateway.config.GatewayRedisAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayResilience4JCircuitBreakerAutoConfiguration",
        "org.springframework.cloud.gateway.config.SimpleUrlHandlerMappingGlobalCorsAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayReactiveOAuth2AutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayMetricsAutoConfiguration",
        "org.springframework.cloud.gateway.config.LocalResponseCacheAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayNoLoadBalancerClientAutoConfiguration",
        "org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration"
})
@EnableFeignClients(basePackages = "com.quanxiaoha.xiaolanshu")
@MapperScan("com.quanxiaoha.xiaolanshu.auth.domain.mapper")
public class xiaolanshuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(xiaolanshuAuthApplication.class, args);
    }

}

