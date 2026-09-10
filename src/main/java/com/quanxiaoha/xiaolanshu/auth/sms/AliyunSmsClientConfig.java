package com.quanxiaoha.xiaolanshu.auth.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
    public Client smsClient() {
        String accessKeyId = aliyunAccessKeyProperties.getAccessKeyId();
        String accessKeySecret = aliyunAccessKeyProperties.getAccessKeySecret();
        if (!StringUtils.hasText(accessKeyId) || !StringUtils.hasText(accessKeySecret)) {
            log.warn("未配置阿里云短信凭证，短信发送功能将保持禁用状态");
            return null;
        }

        try {
            Config config = new Config()
                    // 必填
                    .setAccessKeyId(accessKeyId)
                    // 必填
                    .setAccessKeySecret(accessKeySecret);

            // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
            config.endpoint = "dysmsapi.aliyuncs.com";

            return new Client(config);
        } catch (Exception e) {
            log.error("初始化阿里云短信发送客户端错误: ", e);
            return null;
        }
    }
}

