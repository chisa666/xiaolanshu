package com.quanxiaoha.xiaolanshu.count.biz.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.count.dto.FindNoteCountsByIdRspDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindNoteCountsByIdsReqDTO;

import java.util.List;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 笔记计数业务
 **/
public interface NoteCountService {

    /**
     * 批量查询笔记计数
     * @param findNoteCountsByIdsReqDTO
     * @return
     */
    Response<List<FindNoteCountsByIdRspDTO>> findNotesCountData(FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO);
}

