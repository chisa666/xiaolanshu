package com.quanxiaoha.xiaolanshu.note.biz.domain.mapper;

import com.quanxiaoha.xiaolanshu.note.biz.domain.dataobject.TopicDO;

public interface TopicDOMapper {
    /**
     * 根据主键 ID 查询话题
     */
    String selectNameByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(TopicDO record);

    int insertSelective(TopicDO record);

    TopicDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicDO record);

    int updateByPrimaryKey(TopicDO record);
}

