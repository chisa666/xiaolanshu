package com.quanxiaoha.xiaolanshu.auth.domain.mapper;

import com.quanxiaoha.xiaolanshu.auth.domain.dataobject.UserDO;

public interface UserDOMapper {

    /**
     * 根据主键 ID 查询
     * @param id
     * @return
     */
    UserDO selectByPrimaryKey(Long id);

    UserDO selectByPhone(String phone);

    /**
     * 根据主键 ID 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     * @param record
     * @return
     */
    int insert(UserDO record);

    /**
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(UserDO record);
}

