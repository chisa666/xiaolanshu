package com.quanxiaoha.xiaolanshu.count.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 聚合后计数：收藏、取消收藏笔记
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AggregationCountCollectUnCollectNoteMqDTO {

    private Long creatorId;

    private Long noteId;

    private Integer count;
}
