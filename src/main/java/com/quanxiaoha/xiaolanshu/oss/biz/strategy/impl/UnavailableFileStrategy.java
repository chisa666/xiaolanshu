package com.quanxiaoha.xiaolanshu.oss.biz.strategy.impl;

import com.quanxiaoha.xiaolanshu.oss.biz.strategy.FileStrategy;
import org.springframework.web.multipart.MultipartFile;

/**
 * Keeps the OSS service startable when object storage is not configured.
 */
public class UnavailableFileStrategy implements FileStrategy {

    private final String strategyType;

    public UnavailableFileStrategy(String strategyType) {
        this.strategyType = strategyType;
    }

    @Override
    public String uploadFile(MultipartFile file, String bucketName) {
        throw new IllegalStateException("文件存储未配置或未启用: " + strategyType
                + ". 请设置 STORAGE_TYPE 和对应的 *_ENABLED 环境变量");
    }
}