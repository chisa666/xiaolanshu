package com.quanxiaoha.xiaolanshu.user.biz.controller;

import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.user.biz.model.vo.FindUserProfileReqVO;
import com.quanxiaoha.xiaolanshu.user.biz.model.vo.FindUserProfileRspVO;
import com.quanxiaoha.xiaolanshu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.quanxiaoha.xiaolanshu.user.biz.service.UserService;
import com.quanxiaoha.xiaolanshu.user.dto.req.*;
import com.quanxiaoha.xiaolanshu.user.dto.resp.FindUserByIdRspDTO;
import com.quanxiaoha.xiaolanshu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 修改用户信息
     */
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> updateUserInfo(@Validated UpdateUserInfoReqVO updateUserInfoReqVO) {
        return userService.updateUserInfo(updateUserInfoReqVO);
    }

    /**
     * 获取用户主页信息
     *
     * @return
     */
    @PostMapping(value = "/profile")
    public Response<FindUserProfileRspVO> findUserProfile(@Validated @RequestBody FindUserProfileReqVO findUserProfileReqVO) {
        return userService.findUserProfile(findUserProfileReqVO);
    }

    // ===================================== 对其他服务提供的接口 =====================================
    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<Long> register(@Validated @RequestBody RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }

    @PostMapping("/findByPhone")
    @ApiOperationLog(description = "手机号查询用户信息")
    public Response<FindUserByPhoneRspDTO> findByPhone(@Validated @RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO){
        return userService.findByPhone(findUserByPhoneReqDTO);
    }

    @PostMapping("/password/update")
    @ApiOperationLog(description = "密码更新")
    public Response<?> updatePassword(@Validated @RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO){
        return userService.updatePassword(updateUserPasswordReqDTO);
    }

    @PostMapping("/findById")
    @ApiOperationLog(description = "通过id查询用户信息")
    public Response<FindUserByIdRspDTO> findById(@Validated @RequestBody FindUserByIdReqDTO  findUserByIdReqDTO){
        return userService.findById(findUserByIdReqDTO);
    }

    @PostMapping("/findByIds")
    @ApiOperationLog(description = "批量查询用户信息")
    public Response<List<FindUserByIdRspDTO>> findByIds(@Validated @RequestBody FindUsersByIdsReqDTO findUsersByIdsReqDTO) {
        return userService.findByIds(findUsersByIdsReqDTO);
    }

}


