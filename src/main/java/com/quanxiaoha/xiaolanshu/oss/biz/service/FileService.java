package com.quanxiaoha.xiaolanshu.oss.biz.service;

import com.quanxiaoha.framework.common.response.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 文件服务
 **/
public interface FileService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    Response<?> uploadFile(MultipartFile file);
}

