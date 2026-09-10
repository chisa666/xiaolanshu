package com.quanxiaoha.xiaolanshu.kv.biz.service.impl;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.kv.biz.domain.dataobject.CommentContentDO;
import com.quanxiaoha.xiaolanshu.kv.biz.domain.dataobject.CommentContentPrimaryKey;
import com.quanxiaoha.xiaolanshu.kv.biz.service.CommentContentService;
import com.quanxiaoha.xiaolanshu.kv.dto.req.BatchAddCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.BatchFindCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.CommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.DeleteCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.FindCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.rsp.FindCommentContentRspDTO;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.quanxiaoha.xiaolanshu.kv.biz.domain.repository.CommentContentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 评论内容业务
 **/
@Service
@Slf4j
public class CommentContentServiceImpl implements CommentContentService {

    @Resource
    private CassandraTemplate cassandraTemplate;

    @Resource
    private CommentContentRepository commentContentRepository;

    /**
     * 批量添加评论内容
     *
     * @param batchAddCommentContentReqDTO
     * @return
     */
    @Override
    public Response<?> batchAddCommentContent(BatchAddCommentContentReqDTO batchAddCommentContentReqDTO) {
        List<CommentContentReqDTO> comments = batchAddCommentContentReqDTO.getComments();

        // DTO 转 DO
        List<CommentContentDO> contentDOS = comments.stream()
                .map(commentContentReqDTO -> {
                    // 构建主键类
                    CommentContentPrimaryKey commentContentPrimaryKey = CommentContentPrimaryKey.builder()
                            .noteId(commentContentReqDTO.getNoteId())
                            .yearMonth(commentContentReqDTO.getYearMonth())
                            .contentId(UUID.fromString(commentContentReqDTO.getContentId()))
                            .build();

                    // DO 实体类
                    CommentContentDO commentContentDO = CommentContentDO.builder()
                            .primaryKey(commentContentPrimaryKey)
                            .content(commentContentReqDTO.getContent())
                            .build();

                    return commentContentDO;
                }).toList();

        // 批量插入
        cassandraTemplate.batchOps()
                .insert(contentDOS)
                .execute();

        return Response.success();
    }

    @Override
    public Response<?> batchFindCommentContent(BatchFindCommentContentReqDTO req) {
        Long noteId = req.getNoteId();
        List<FindCommentContentReqDTO> keys = req.getCommentContentKeys();
        List<String> yearMonths = keys.stream().map(FindCommentContentReqDTO::getYearMonth).distinct().toList();
        List<UUID> contentIds = keys.stream().map(k -> UUID.fromString(k.getContentId())).distinct().toList();
        List<CommentContentDO> dos = commentContentRepository
                .findByPrimaryKeyNoteIdAndPrimaryKeyYearMonthInAndPrimaryKeyContentIdIn(noteId, yearMonths, contentIds);
        List<FindCommentContentRspDTO> result = Lists.newArrayList();
        if (CollUtil.isNotEmpty(dos)) {
            result = dos.stream().map(d -> FindCommentContentRspDTO.builder()
                    .contentId(String.valueOf(d.getPrimaryKey().getContentId()))
                    .content(d.getContent()).build()).toList();
        }
        return Response.success(result);
    }

    @Override
    public Response<?> deleteCommentContent(DeleteCommentContentReqDTO req) {
        commentContentRepository.deleteByPrimaryKeyNoteIdAndPrimaryKeyYearMonthAndPrimaryKeyContentId(
                req.getNoteId(), req.getYearMonth(), UUID.fromString(req.getContentId()));
        return Response.success();
    }
}

