package com.quanxiaoha.xiaolanshu.kv.biz.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.kv.dto.req.AddNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.FindNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.rsp.FindNoteContentRspDTO;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 笔记内容存储业务
 **/
public interface NoteContentService {

    /**
     * 添加笔记内容
     *
     * @param addNoteContentReqDTO
     * @return
     */
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);

    Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO req);

    /**
     * 删除笔记内容
     */
    Response<?> deleteNoteContent(DeleteNoteContentReqDTO req);

}

