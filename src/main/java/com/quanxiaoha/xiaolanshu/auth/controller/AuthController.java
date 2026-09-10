package com.quanxiaoha.xiaolanshu.auth.controller;

import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UserLoginReqVO;
import com.quanxiaoha.xiaolanshu.auth.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录/注册")
    public Response<String> loginAndRegister(@Validated @RequestBody UserLoginReqVO req) {
        return authService.loginAndRegister(req);
    }

    @PostMapping("/logout")
    @ApiOperationLog(description = "账号登出")
    public Response<?> logout() {
        return authService.logout();
    }

    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Validated @RequestBody UpdatePasswordReqVO req) {
        return authService.updatePassword(req);
    }
}
