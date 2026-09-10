package com.quanxiaoha.xiaolanshu.user.biz.domain.mapper;

import com.quanxiaoha.xiaolanshu.user.biz.domain.dataobject.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleDOMapper {
    int insert(UserRoleDO record);
}
