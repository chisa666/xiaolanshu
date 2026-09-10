package com.quanxiaoha.xiaolanshu.auth.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UserLoginReqVO;

public interface AuthService {
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);
    Response<?> logout();
    Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO);
}
