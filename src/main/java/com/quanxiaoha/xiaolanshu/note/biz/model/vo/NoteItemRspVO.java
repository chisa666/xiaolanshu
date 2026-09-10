package com.quanxiaoha.xiaolanshu.note.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 笔记卡片展示数据
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteItemRspVO {

    /**
     * 笔记 ID
     */
    private Long noteId;

    /**
     * 笔记类型 (0：图文 1：视频)
     */
    private Integer type;

    /**
     * 图文笔记封面
     */
    private String cover;

    /**
     * 视频文件链接
     */
    private String videoUri;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 发布者 ID
     */
    private Long creatorId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 被点赞数
     */
    private String likeTotal;

    private Boolean isLiked;

}

