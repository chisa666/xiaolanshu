package com.quanxiaoha.xiaolanshu.note.biz.domain.mapper;

import com.quanxiaoha.xiaolanshu.note.biz.domain.dataobject.NoteDO;
import com.quanxiaoha.xiaolanshu.note.biz.domain.dataobject.NoteLikeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteDOMapper {

    // 笔记仅对自己可见接口
    int  updateVisibleOnlyMe(NoteDO noteDO);

    //  更新笔记置顶状态
    int updateIsTop(NoteDO noteDO);

    // 根据笔记 ID 查询笔记是否存在
    int selectCountByNoteId(Long noteId);

    /**
     * 查询笔记的发布者用户 ID
     * @param noteId
     * @return
     */
    Long selectCreatorIdByNoteId(Long noteId);


    /**
     * 查询个人主页已发布笔记列表
     * @param creatorId
     * @param cursor
     * @return
     */
    List<NoteDO> selectPublishedNoteListByUserIdAndCursor(@Param("creatorId") Long creatorId,
                                                          @Param("cursor") Long cursor);




    int deleteByPrimaryKey(Long id);

    int insert(NoteDO record);

    int insertSelective(NoteDO record);

    NoteDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteDO record);

    int updateByPrimaryKey(NoteDO record);
}

