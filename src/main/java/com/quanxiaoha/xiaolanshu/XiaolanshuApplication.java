package com.quanxiaoha.xiaolanshu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayClassPathWarningAutoConfiguration;
import cn.dev33.satoken.reactor.spring.SaTokenContextRegister;
import com.alibaba.cloud.sentinel.SentinelWebAutoConfiguration;

/**
 * 可直接启动的本地开发入口。
 *
 * 教程中的各微服务启动类仍按服务包保留；这个入口只扫描 app 包，
 * 便于在没有启动全部基础设施时先验证项目主链路。
 */
@SpringBootApplication(
        scanBasePackages = "com.quanxiaoha.xiaolanshu.app",
        exclude = {
                DataSourceAutoConfiguration.class,
                RedisAutoConfiguration.class,
                RedisRepositoriesAutoConfiguration.class,
                MybatisAutoConfiguration.class,
                GatewayAutoConfiguration.class,
                GatewayClassPathWarningAutoConfiguration.class,
                SaTokenContextRegister.class,
                SentinelWebAutoConfiguration.class,
                CassandraAutoConfiguration.class,
                CassandraDataAutoConfiguration.class,
                CassandraRepositoriesAutoConfiguration.class
        })
public class XiaolanshuApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaolanshuApplication.class, args);
    }
}

