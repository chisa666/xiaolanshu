package com.quanxiaoha.xiaolanshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: chisa
 * @url: www.quanxiaoha.com
 * @description: 笔记操作
 **/
@Getter
@AllArgsConstructor
public enum NoteOperateEnum {
    // 笔记发布
    PUBLISH(1),
    // 笔记删除
    DELETE(0),
    ;

    private final Integer code;

}

