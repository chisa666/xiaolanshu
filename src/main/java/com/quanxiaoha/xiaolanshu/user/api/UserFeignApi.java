package com.quanxiaoha.xiaolanshu.user.api;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.user.constant.ApiConstants;
import com.quanxiaoha.xiaolanshu.user.dto.req.FindUserByIdReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.FindUserByPhoneReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.FindUsersByIdsReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.RegisterUserReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.resp.FindUserByIdRspDTO;
import com.quanxiaoha.xiaolanshu.user.dto.resp.FindUserByPhoneRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface UserFeignApi {
    String PREFIX = "/user";

    @PostMapping(value = PREFIX + "/findByIds")
    Response<List<FindUserByIdRspDTO>> findByIds(@RequestBody FindUsersByIdsReqDTO req);

    @PostMapping(value = PREFIX + "/findByPhone")
    Response<FindUserByPhoneRspDTO> findByPhone(@RequestBody FindUserByPhoneReqDTO req);

    @PostMapping(value = PREFIX + "/register")
    Response<Long> registerUser(@RequestBody RegisterUserReqDTO req);

    @PostMapping(value = PREFIX + "/password/update")
    Response<?> updatePassword(@RequestBody UpdateUserPasswordReqDTO req);

    @PostMapping(value = PREFIX + "/findById")
    Response<FindUserByIdRspDTO> findById(@RequestBody FindUserByIdReqDTO req);
}
