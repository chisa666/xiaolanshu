package com.quanxiaoha.xiaolanshu.auth.sms;

import com.aliyun.teaopenapi.models.Config;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 短信发送客户端
 **/
@Configuration
@Slf4j
public class AliyunSmsClientConfig {

    @Resource
    private AliyunAccessKeyProperties aliyunAccessKeyProperties;

    @Bean
    @ConditionalOnProperty(prefix = "aliyun.sms", name = "provider", havingValue = "dysmsapi", matchIfMissing = true)
    public com.aliyun.dysmsapi20170525.Client dysmsapiClient() {
        Config config = createConfig("dysmsapi.aliyuncs.com");
        if (config == null) {
            return null;
        }
        try {
            return new com.aliyun.dysmsapi20170525.Client(config);
        } catch (Exception e) {
            log.error("初始化阿里云短信服务客户端错误: ", e);
            return null;
        }
    }

    @Bean
    @ConditionalOnProperty(prefix = "aliyun.sms", name = "provider", havingValue = "dypnsapi")
    public com.aliyun.dypnsapi20170525.Client dypnsapiClient() {
        Config config = createConfig("dypnsapi.aliyuncs.com");
        if (config == null) {
            return null;
        }
        try {
            return new com.aliyun.dypnsapi20170525.Client(config);
        } catch (Exception e) {
            log.error("初始化阿里云号码认证客户端错误: ", e);
            return null;
        }
    }

    private Config createConfig(String endpoint) {
        String accessKeyId = aliyunAccessKeyProperties.getAccessKeyId();
        String accessKeySecret = aliyunAccessKeyProperties.getAccessKeySecret();
        if (!StringUtils.hasText(accessKeyId) || !StringUtils.hasText(accessKeySecret)) {
            log.warn("未配置阿里云短信凭证，短信发送功能将保持禁用状态");
            return null;
        }
        return new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint(endpoint);
    }
}
