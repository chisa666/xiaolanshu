package com.quanxiaoha.xiaolanshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: chisa
 * @url: www.quanxiaoha.com
 * @description: 笔记可见性
 **/
@Getter
@AllArgsConstructor
public enum NoteVisibleEnum {

    PUBLIC(0), // 公开，所有人可见
    PRIVATE(1); // 仅自己可见

    private final Integer code;

}

