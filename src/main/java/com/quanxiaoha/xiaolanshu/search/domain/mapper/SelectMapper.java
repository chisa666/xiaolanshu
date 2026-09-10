package com.quanxiaoha.xiaolanshu.search.domain.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 查询
 */
public interface SelectMapper {

    /**
     * 查询笔记文档所需的全字段数据
     * @param noteId
     * @return
     */
    List<Map<String, Object>> selectEsNoteIndexData(@Param("noteId") Long noteId, @Param("userId") Long userId);
}

