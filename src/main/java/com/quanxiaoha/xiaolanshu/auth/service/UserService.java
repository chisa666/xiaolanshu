package com.quanxiaoha.xiaolanshu.auth.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UserLoginReqVO;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 用户认证服务
 **/
public interface UserService {

    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);

    /**
     * 修改密码
     * @param updatePasswordReqVO
     * @return
     */
    Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO);
}

