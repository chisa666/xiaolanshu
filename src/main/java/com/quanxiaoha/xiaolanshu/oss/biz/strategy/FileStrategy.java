package com.quanxiaoha.xiaolanshu.oss.biz.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 文件策略接口
 **/
public interface FileStrategy {

    /**
     * 文件上传
     *
     * @param file
     * @param bucketName
     * @return
     */
    String uploadFile(MultipartFile file, String bucketName);

}

