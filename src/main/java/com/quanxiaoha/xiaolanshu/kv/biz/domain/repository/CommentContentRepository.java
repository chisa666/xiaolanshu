package com.quanxiaoha.xiaolanshu.kv.biz.domain.repository;

import com.quanxiaoha.xiaolanshu.kv.biz.domain.dataobject.CommentContentDO;
import com.quanxiaoha.xiaolanshu.kv.biz.domain.dataobject.CommentContentPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 评论内容 Cassandra 仓储
 **/
public interface CommentContentRepository extends CassandraRepository<CommentContentDO, CommentContentPrimaryKey> {

    /**
     * 批量查询评论内容
     * @param noteId
     * @param yearMonths
     * @param contentIds
     * @return
     */
    List<CommentContentDO> findByPrimaryKeyNoteIdAndPrimaryKeyYearMonthInAndPrimaryKeyContentIdIn(
            Long noteId, List<String> yearMonths, List<UUID> contentIds
    );

    /**
     * 删除评论正文
     */
    void deleteByPrimaryKeyNoteIdAndPrimaryKeyYearMonthAndPrimaryKeyContentId(
            Long noteId, String yearMonth, UUID contentId
    );
}

