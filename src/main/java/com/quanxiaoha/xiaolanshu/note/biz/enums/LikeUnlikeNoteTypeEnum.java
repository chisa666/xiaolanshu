package com.quanxiaoha.xiaolanshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: chisa
 * @url: www.quanxiaoha.com
 * @description: 笔记点赞、取消点赞 Type
 **/
@Getter
@AllArgsConstructor
public enum LikeUnlikeNoteTypeEnum {
    // 点赞
    LIKE(1),
    // 取消点赞
    UNLIKE(0),
    ;

    private final Integer code;

}

