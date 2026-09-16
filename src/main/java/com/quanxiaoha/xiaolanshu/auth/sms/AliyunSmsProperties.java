package com.quanxiaoha.xiaolanshu.auth.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.sms")
@Component
@Data
public class AliyunSmsProperties {

    private String provider = "dysmsapi";
    private String signName = "阿里云短信测试";
    private String templateCode = "SMS_154950909";
    private int validityMinutes = 3;
    private int connectTimeoutMillis = 5000;
    private int readTimeoutMillis = 10000;
}