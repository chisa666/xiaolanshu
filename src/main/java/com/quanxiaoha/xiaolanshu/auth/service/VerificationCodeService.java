package com.quanxiaoha.xiaolanshu.auth.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;

public interface VerificationCodeService {

    /**
     * 发送短信验证码
     *
     * @param sendVerificationCodeReqVO
     * @return
     */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);
}

