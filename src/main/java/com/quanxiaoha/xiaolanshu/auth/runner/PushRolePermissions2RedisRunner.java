package com.quanxiaoha.xiaolanshu.auth.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.auth.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.PermissionDO;
import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.RoleDO;
import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.RolePermissionDO;
import com.quanxiaoha.xiaolanshu.auth.domain.mapper.PermissionDOMapper;
import com.quanxiaoha.xiaolanshu.auth.domain.mapper.RoleDOMapper;
import com.quanxiaoha.xiaolanshu.auth.domain.mapper.RolePermissionDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 推送角色权限数据到 Redis 中
 **/
@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

    private static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource
    private PermissionDOMapper permissionDOMapper;
    @Resource
    private RolePermissionDOMapper rolePermissionDOMapper;

    @Override
    public void run(ApplicationArguments args) {
        log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");

        try {
            Boolean canPush = redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS);
            if (!Boolean.TRUE.equals(canPush)) {
                log.info("==> 角色权限数据已经同步至 Redis 中，本次启动跳过同步...");
                return;
            }

            // 查询出所有角色
            List<RoleDO> roleDOS = roleDOMapper.selectEnabledList();

            if (CollUtil.isNotEmpty(roleDOS)) {
                // 拿到所有角色的 ID
                List<Long> roleIds = roleDOS.stream().map(RoleDO::getId).toList();

                // 根据角色 ID, 批量查询出所有角色对应的权限
                List<RolePermissionDO> rolePermissionDOS = rolePermissionDOMapper.selectByRoleIds(roleIds);
                if (CollUtil.isEmpty(rolePermissionDOS)) {
                    rolePermissionDOS = List.of();
                }
                // 按角色 ID 分组, 每个角色 ID 对应多个权限 ID
                Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionDOS.stream().collect(
                        Collectors.groupingBy(RolePermissionDO::getRoleId,
                                Collectors.mapping(RolePermissionDO::getPermissionId, Collectors.toList()))
                );

                // 查询 APP 端所有被启用的权限
                List<PermissionDO> permissionDOS = permissionDOMapper.selectAppEnabledList();
                if (CollUtil.isEmpty(permissionDOS)) {
                    permissionDOS = List.of();
                }
                // 权限 ID - 权限 DO
                Map<Long, PermissionDO> permissionIdDOMap = permissionDOS.stream().collect(
                        Collectors.toMap(PermissionDO::getId, permissionDO -> permissionDO)
                );

                // 组织 角色标识-权限标识关系。网关按 roleKey 读取权限字符串集合。
                Map<String, List<String>> roleKeyPermissionsMap = Maps.newHashMap();

                // 循环所有角色
                roleDOS.forEach(roleDO -> {
                    // 当前角色 ID
                    Long roleId = roleDO.getId();
                    // 当前角色 ID 对应的权限 ID 集合
                    String roleKey = roleDO.getRoleKey();
                    List<Long> permissionIds = roleIdPermissionIdsMap.getOrDefault(roleId, List.of());
                    if (CollUtil.isNotEmpty(permissionIds)) {
                        List<String> permissionKeys = new java.util.ArrayList<>(permissionIds.size());
                        permissionIds.forEach(permissionId -> {
                            // 根据权限 ID 获取具体的权限 DO 对象
                            PermissionDO permissionDO = permissionIdDOMap.get(permissionId);
							if (Objects.nonNull(permissionDO)) {
								permissionKeys.add(permissionDO.getPermissionKey());
							}
                        });
                        roleKeyPermissionsMap.put(roleKey, permissionKeys);
                    }
                });

                // 同步至 Redis 中，方便后续网关查询鉴权使用
                roleKeyPermissionsMap.forEach((roleKey, permissions) -> {
                    String key = RedisKeyConstants.buildRolePermissionsKey(roleKey);
                    redisTemplate.opsForValue().set(key, JsonUtils.toJsonString(permissions));
                });
            }

            log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
        } catch (Exception e) {
            log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
        }

    }
}

