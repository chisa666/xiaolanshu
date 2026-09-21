package com.quanxiaoha.xiaolanshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: chisa
 * @description: 笔记收藏、取消收藏 Type
 **/
@Getter
@AllArgsConstructor
public enum CollectUnCollectNoteTypeEnum {
    // 收藏
    COLLECT(1),
    // 取消收藏
    UN_COLLECT(0),
    ;

    private final Integer code;

}

