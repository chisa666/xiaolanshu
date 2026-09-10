package com.quanxiaoha.xiaolanshu.data.align.domain.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 查询
 */
public interface SelectMapper {

    /**
     * 日增量表：关注数计数变更 - 批量查询
     * @param tableNameSuffix
     * @param batchSize
     * @return
     */
    List<Long> selectBatchFromDataAlignFollowingCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                               @Param("batchSize") int batchSize);

    /**
     * 查询 t_following 关注表，获取关注总数
     * @param userId
     * @return
     */
    int selectCountFromFollowingTableByUserId(long userId);

    List<Long> selectBatchFromDataAlignFansCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                          @Param("batchSize") int batchSize);

    int selectCountFromFansTableByUserId(long userId);

    List<Long> selectBatchFromDataAlignUserLikeCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                              @Param("batchSize") int batchSize);

    int selectUserLikeCountFromNoteLikeTableByUserId(long userId);

    List<Long> selectBatchFromDataAlignUserCollectCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                                 @Param("batchSize") int batchSize);

    int selectUserCollectCountFromNoteCollectionTableByUserId(long userId);

    List<Long> selectBatchFromDataAlignNoteLikeCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                              @Param("batchSize") int batchSize);

    int selectCountFromNoteLikeTableByUserId(long noteId);

    List<Long> selectBatchFromDataAlignNotePublishCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                                 @Param("batchSize") int batchSize);

    int selectCountFromNoteTableByUserId(long userId);

    List<Long> selectBatchFromDataAlignNoteCollectCountTempTable(@Param("tableNameSuffix") String tableNameSuffix,
                                                                 @Param("batchSize") int batchSize);

    int selectCountFromNoteCollectionTableByUserId(long noteId);
}

