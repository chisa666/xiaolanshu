package com.quanxiaoha.xiaolanshu.auth.rpc;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.user.api.UserFeignApi;
import com.quanxiaoha.xiaolanshu.user.dto.req.RegisterUserReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.UpdateUserPasswordReqDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 用户服务
 **/
@Component
public class UserRpcService {

    @Resource
    private UserFeignApi userFeignApi;

    /**
     * 用户注册
     *
     * @param phone
     * @return
     */
    public Long registerUser(String phone) {
        RegisterUserReqDTO registerUserReqDTO = new RegisterUserReqDTO();
        registerUserReqDTO.setPhone(phone);

        Response<Long> response = userFeignApi.registerUser(registerUserReqDTO);

        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

    public void updatePassword(String encodePassword) {
        userFeignApi.updatePassword(UpdateUserPasswordReqDTO.builder().encodePassword(encodePassword).build());
    }

}

