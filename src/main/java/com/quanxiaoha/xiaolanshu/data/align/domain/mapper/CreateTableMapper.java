package com.quanxiaoha.xiaolanshu.data.align.domain.mapper;

/**
 * 自动创建表
 */
public interface CreateTableMapper {

    /**
     * 创建日增量表：关注数计数变更
     * @param tableNameSuffix
     */
    void createDataAlignFollowingCountTempTable(String tableNameSuffix);

    void createDataAlignFansCountTempTable(String tableNameSuffix);
    void createDataAlignNoteCollectCountTempTable(String tableNameSuffix);
    void createDataAlignUserCollectCountTempTable(String tableNameSuffix);
    void createDataAlignUserLikeCountTempTable(String tableNameSuffix);
    void createDataAlignNoteLikeCountTempTable(String tableNameSuffix);
    void createDataAlignNotePublishCountTempTable(String tableNameSuffix);
}

