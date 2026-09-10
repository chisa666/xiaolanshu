package com.quanxiaoha.xiaolanshu.comment.biz.domain.mapper;

import com.quanxiaoha.xiaolanshu.comment.biz.domain.dataobject.NoteCountDO;
import org.apache.ibatis.annotations.Param;

public interface NoteCountDOMapper {

    /**
     * 查询笔记评论总数
     * @param noteId
     * @return
     */
    Long selectCommentTotalByNoteId(Long noteId);

    /**
     * 更新评论总数
     * @param noteId
     * @param count
     * @return
     */
    int updateCommentTotalByNoteId(@Param("noteId") Long noteId,
                                   @Param("count") int count);

    int deleteByPrimaryKey(Long id);

    int insert(NoteCountDO record);

    int insertSelective(NoteCountDO record);

    NoteCountDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteCountDO record);

    int updateByPrimaryKey(NoteCountDO record);
}



