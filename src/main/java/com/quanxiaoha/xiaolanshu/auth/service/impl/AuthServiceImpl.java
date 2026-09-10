package com.quanxiaoha.xiaolanshu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.quanxiaoha.framework.biz.context.holder.LoginUserContextHolder;
import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.auth.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.auth.enums.LoginTypeEnum;
import com.quanxiaoha.xiaolanshu.auth.enums.ResponseCodeEnum;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UserLoginReqVO;
import com.quanxiaoha.xiaolanshu.auth.rpc.UserRpcService;
import com.quanxiaoha.xiaolanshu.auth.service.AuthService;
import com.quanxiaoha.xiaolanshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRpcService userRpcService;

    @Override
    public Response<String> loginAndRegister(UserLoginReqVO req) {
        LoginTypeEnum loginType = LoginTypeEnum.valueOf(req.getType());
        if (loginType == null) {
            throw new BizException(ResponseCodeEnum.LOGIN_TYPE_ERROR);
        }

        Long userId;
        if (loginType == LoginTypeEnum.VERIFICATION_CODE) {
            Preconditions.checkArgument(StringUtils.isNotBlank(req.getCode()), "验证码不能为空");
            Object stored = redisTemplate.opsForValue().get(RedisKeyConstants.buildVerificationCodeKey(req.getPhone()));
            if (!StringUtils.equals(req.getCode(), Objects.toString(stored, null))) {
                throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
            }
            userId = userRpcService.registerUser(req.getPhone());
            if (userId == null) {
                throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
            }
        } else {
            FindUserByPhoneRspDTO user = userRpcService.findUserByPhone(req.getPhone());
            if (user == null) {
                throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
            }
            if (StringUtils.isBlank(user.getPassword())
                    || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
            }
            userId = user.getId();
        }

        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return Response.success(tokenInfo.getTokenValue());
    }

    @Override
    public Response<?> logout() {
        Long userId = LoginUserContextHolder.getUserId();
        if (userId != null) {
            StpUtil.logout(userId);
        }
        return Response.success();
    }

    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO req) {
        userRpcService.updatePassword(passwordEncoder.encode(req.getNewPassword()));
        return Response.success();
    }
}
