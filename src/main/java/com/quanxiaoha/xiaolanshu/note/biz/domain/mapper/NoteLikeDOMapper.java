package com.quanxiaoha.xiaolanshu.note.biz.domain.mapper;

import com.quanxiaoha.xiaolanshu.note.biz.domain.dataobject.NoteLikeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteLikeDOMapper {

    //查询笔记是否点赞
    int selectCountByUserIdAndNoteId(@Param("userId") Long userId, @Param("noteId") Long noteId);

    //查询当前用户所有点赞的笔记
    List<NoteLikeDO> selectByUserId(@Param("userId") Long userId);

    // 查询笔记是否被点赞
    int selectNoteIsLiked(@Param("userId") Long userId, @Param("noteId") Long noteId);

    //查询用户最新点赞的笔记查询方法
    List<NoteLikeDO> selectLikedByUserIdAndLimit(@Param("userId") Long userId, @Param("limit")  int limit);

    /**
     * 新增笔记点赞记录，若已存在，则更新笔记点赞记录 -- 用户点赞
     * @param noteLikeDO
     * @return
     */
    int insertOrUpdate(NoteLikeDO noteLikeDO);

    /**
     * 批量插入或更新
     * @param noteLikeDOS
     * @return
     */
    int batchInsertOrUpdate(@Param("noteLikeDOS") List<NoteLikeDO> noteLikeDOS);

    /**
     * 删除笔记点赞记录 -- 用户取消点赞
     * @param  noteLikeDO
     * @return
     */
    int update2UnlikeByUserIdAndNoteId(NoteLikeDO noteLikeDO);

    /**
     * 查询某用户，对于一批量笔记的已点赞记录
     * @param userId
     * @param noteIds
     * @return
     */
    List<NoteLikeDO> selectByUserIdAndNoteIds(@Param("userId") Long userId,
                                              @Param("noteIds") List<Long> noteIds);

    int deleteByPrimaryKey(Long id);

    int insert(NoteLikeDO record);

    int insertSelective(NoteLikeDO record);

    NoteLikeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteLikeDO record);

    int updateByPrimaryKey(NoteLikeDO record);
}

