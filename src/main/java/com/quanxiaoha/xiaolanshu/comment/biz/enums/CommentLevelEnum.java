package com.quanxiaoha.xiaolanshu.comment.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Objects;

/**
 * @author: chisa
 * @url: www.quanxiaoha.com
 * @description: 评论级别
 **/
@Getter
@AllArgsConstructor
public enum CommentLevelEnum {
    // 一级评论
    ONE(1),
    // 二级评论
    TWO(2),
    ;

    private final Integer code;

    public static CommentLevelEnum valueOf(Integer code) {
        for (CommentLevelEnum value : values()) if (Objects.equals(value.code, code)) return value;
        return null;
    }

}

