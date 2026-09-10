package com.quanxiaoha.xiaolanshu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.quanxiaoha.framework.common.enums.DeletedEnum;
import com.quanxiaoha.framework.common.enums.StatusEnum;
import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.auth.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.auth.constant.RoleConstants;
import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.PermissionDO;
import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.RoleDO;
import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.UserDO;
import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.UserRoleDO;
import com.quanxiaoha.xiaolanshu.auth.domain.mapper.UserDOMapper;
import com.quanxiaoha.xiaolanshu.auth.domain.mapper.UserRoleDOMapper;
import com.quanxiaoha.xiaolanshu.auth.enums.LoginTypeEnum;
import com.quanxiaoha.xiaolanshu.auth.enums.ResponseCodeEnum;
import com.quanxiaoha.xiaolanshu.auth.model.vo.user.UserLoginReqVO;
import com.quanxiaoha.xiaolanshu.auth.service.UserService;
import com.quanxiaoha.xiaolanshu.auth.rpc.UserRpcService;
import com.quanxiaoha.framework.biz.context.holder.LoginUserContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleDOMapper userRoleDOMapper;
    @Resource
    private UserRpcService userRpcService;

    /**
     * 登录与注册
     *
     * @param userLoginReqVO
     * @return
     */
    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(userLoginReqVO.getType());
        if (loginTypeEnum == null) {
            return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode(), "登录类型错误");
        }
        Long userId = null;
        UserDO userDO = null;

        // 判断登录类型
        switch (loginTypeEnum) {
            case VERIFICATION_CODE: // 验证码登录
                String verificationCode = userLoginReqVO.getCode();
                if (StringUtils.isBlank(verificationCode)) {
                    return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode(), "验证码不能为空");
                }
                String code = String.valueOf(redisTemplate.opsForValue().get(RedisKeyConstants.buildVerificationCodeKey(phone)));
                if (!StringUtils.equals(verificationCode, code) || "null".equals(code)) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }
                userDO = userDOMapper.selectByPhone(phone);

                // 判断是否注册
                if (Objects.isNull(userDO)) {
                    // 若此用户还没有注册，系统自动注册该用户
                    userId = registerUser(phone);
                } else {
                    // 已注册，则获取其用户 ID
                    userId = userDO.getId();
                }
                break;
            case PASSWORD: // 密码登录
                userDO = userDOMapper.selectByPhone(phone);
                if (userDO == null || StringUtils.isBlank(userDO.getPassword())
                        || !passwordEncoder.matches(userLoginReqVO.getPassword(), userDO.getPassword())) {
                    throw new BizException(ResponseCodeEnum.PASSWORD_ERROR);
                }
                userId = userDO.getId();
                break;
            default:
                break;
        }

        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return Response.success(tokenInfo.getTokenValue());
    }

    /**
     * 系统自动注册用户
     * @param phone
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long registerUser(String phone) {
        // 获取全局自增的小哈书 ID
        Long xiaolanshuId = redisTemplate.opsForValue().increment(RedisKeyConstants.xiaolanshu_ID_GENERATOR_KEY);

        UserDO userDO = UserDO.builder()
                .phone(phone)
                .xiaolanshuId(String.valueOf(xiaolanshuId)) // 自动生成小红书号 ID
                .nickname("小红薯" + xiaolanshuId) // 自动生成昵称, 如：小红薯10000
                .status(StatusEnum.ENABLE.getValue()) // 状态为启用
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue()) // 逻辑删除
                .build();

        // 添加入库
        userDOMapper.insert(userDO);

        // 获取刚刚添加入库的用户 ID
        Long userId = userDO.getId();

        // 给该用户分配一个默认角色
        UserRoleDO userRoleDO = UserRoleDO.builder()
                .userId(userId)
                .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue())
                .build();
        userRoleDOMapper.insert(userRoleDO);

        // 将该用户的角色 ID 存入 Redis 中
        List<Long> roles = Lists.newArrayList();
        roles.add(RoleConstants.COMMON_USER_ROLE_ID);
        String userRolesKey = RedisKeyConstants.buildUserRoleKey(phone);
        redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

        return userId;
    }

    @Override
    public Response<?> updatePassword(com.quanxiaoha.xiaolanshu.auth.model.vo.user.UpdatePasswordReqVO req) {
        String encoded = passwordEncoder.encode(req.getNewPassword());
        userRpcService.updatePassword(encoded);
        return Response.success();
    }

}

