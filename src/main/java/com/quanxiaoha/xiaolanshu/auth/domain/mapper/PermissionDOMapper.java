package com.quanxiaoha.xiaolanshu.auth.domain.mapper;

import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.PermissionDO;

import java.util.List;

public interface PermissionDOMapper {
    /**
     * 查询 APP 端所有被启用的权限
     *
     * @return
     */
    List<PermissionDO> selectAppEnabledList();

}

