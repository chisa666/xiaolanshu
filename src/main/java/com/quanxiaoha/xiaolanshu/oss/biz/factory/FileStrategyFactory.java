package com.quanxiaoha.xiaolanshu.oss.biz.factory;

import com.quanxiaoha.xiaolanshu.oss.biz.strategy.FileStrategy;
import com.quanxiaoha.xiaolanshu.oss.biz.strategy.impl.AliyunOSSFileStrategy;
import com.quanxiaoha.xiaolanshu.oss.biz.strategy.impl.MinioFileStrategy;
import com.quanxiaoha.xiaolanshu.oss.biz.strategy.impl.UnavailableFileStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 文件存储策略工厂
 **/
@Configuration
@RefreshScope
public class FileStrategyFactory {

    @Value("${storage.type:none}")
    private String strategyType;

    @Bean
    @RefreshScope
    public FileStrategy getFileStrategy() {
        if (StringUtils.equals(strategyType, "minio")) {
            return new MinioFileStrategy();
        } else if (StringUtils.equals(strategyType, "aliyun")) {
            return new AliyunOSSFileStrategy();
        }

        return new UnavailableFileStrategy(strategyType);
    }

}

