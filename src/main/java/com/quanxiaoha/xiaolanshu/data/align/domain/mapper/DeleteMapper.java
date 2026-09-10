package com.quanxiaoha.xiaolanshu.data.align.domain.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 删除
 */
public interface DeleteMapper {

    /**
     * 日增量表：关注数计数变更 - 批量删除
     * @param userIds
     */
    void batchDeleteDataAlignFollowingCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                     @Param("userIds") List<Long> userIds);

    void batchDeleteDataAlignFansCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                @Param("userIds") List<Long> userIds);

    void batchDeleteDataAlignUserLikeCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                   @Param("userIds") List<Long> userIds);

    void batchDeleteDataAlignUserCollectCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                      @Param("userIds") List<Long> userIds);

    void batchDeleteDataAlignNotePublishCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                      @Param("userIds") List<Long> userIds);

    void batchDeleteDataAlignNoteLikeCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                   @Param("noteIds") List<Long> noteIds);

    void batchDeleteDataAlignNoteCollectCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                      @Param("noteIds") List<Long> noteIds);
}

