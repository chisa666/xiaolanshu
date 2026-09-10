package com.quanxiaoha.xiaolanshu.auth.rpc;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.user.api.UserFeignApi;
import com.quanxiaoha.xiaolanshu.user.dto.req.FindUserByPhoneReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.RegisterUserReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;

    public Long registerUser(String phone) {
        Response<Long> response = userFeignApi.registerUser(
                RegisterUserReqDTO.builder().phone(phone).build());
        return response.isSuccess() ? response.getData() : null;
    }

    public FindUserByPhoneRspDTO findUserByPhone(String phone) {
        Response<FindUserByPhoneRspDTO> response = userFeignApi.findByPhone(
                FindUserByPhoneReqDTO.builder().phone(phone).build());
        return response.isSuccess() ? response.getData() : null;
    }

    public void updatePassword(String encodedPassword) {
        userFeignApi.updatePassword(UpdateUserPasswordReqDTO.builder()
                .encodePassword(encodedPassword)
                .build());
    }
}
