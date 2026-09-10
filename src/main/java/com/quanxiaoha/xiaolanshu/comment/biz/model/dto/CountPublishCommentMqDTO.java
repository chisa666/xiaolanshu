package com.quanxiaoha.xiaolanshu.comment.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 发送给计数服务：评论发布
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountPublishCommentMqDTO {

    /**
     * 笔记 ID
     */
    private Long noteId;

    /**
     * 评论 ID
     */
    private Long commentId;

    private Integer level;

    private Long parentId;

}

