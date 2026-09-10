package com.quanxiaoha.xiaolanshu.kv.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 笔记内容删除
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteNoteContentReqDTO {

    @NotBlank(message = "笔记内容 UUID 不能为空")
    private String uuid;

}

