package com.quanxiaoha.xiaolanshu.auth.domain.mapper;

import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.RoleDO;

import java.util.List;

public interface RoleDOMapper {
    /**
     * 查询所有被启用的角色
     *
     * @return
     */
    List<RoleDO> selectEnabledList();

}

