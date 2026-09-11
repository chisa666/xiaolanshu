package com.quanxiaoha.xiaolanshu.oss.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: chisa
 * @url: www.quanxiaoha.com
 * @description: Minio 配置项
 **/
@ConfigurationProperties(prefix = "storage.minio")
@Component
@Data
public class MinioProperties {
    private boolean enabled;
    private String endpoint;
    private String accessKey;
    private String secretKey;
}

