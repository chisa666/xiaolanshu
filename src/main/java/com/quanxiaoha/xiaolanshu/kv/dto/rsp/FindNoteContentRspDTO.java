package com.quanxiaoha.xiaolanshu.kv.dto.rsp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 笔记内容
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteContentRspDTO {

    /**
     * 笔记内容 UUID
     */
    private UUID uuid;

    public static class FindNoteContentRspDTOBuilder {
        public FindNoteContentRspDTOBuilder noteId(UUID noteId) {
            return uuid(noteId);
        }
    }

    /**
     * 笔记内容
     */
    private String content;

}

