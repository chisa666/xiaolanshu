package com.quanxiaoha.xiaolanshu.auth.domain.mapper;

import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleDOMapper {
    int insert(UserRoleDO record);
}
