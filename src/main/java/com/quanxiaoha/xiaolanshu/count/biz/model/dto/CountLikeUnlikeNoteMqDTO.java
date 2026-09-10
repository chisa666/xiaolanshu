package com.quanxiaoha.xiaolanshu.count.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 点赞、取消点赞笔记
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountLikeUnlikeNoteMqDTO {

    private Long userId;

    private Long noteId;

    private Long noteCreatorId;

    /**
     * 0: 取消点赞， 1：点赞
     */
    private Integer type;

    private LocalDateTime createTime;
}

