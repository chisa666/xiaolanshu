package com.quanxiaoha.xiaolanshu.search.biz.service;

import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.dto.RebuildNoteDocumentReqDTO;
import com.quanxiaoha.xiaolanshu.search.biz.model.vo.SearchNoteReqVO;
import com.quanxiaoha.xiaolanshu.search.biz.model.vo.SearchNoteRspVO;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 笔记搜索业务
 **/
public interface NoteService {

    /**
     * 搜索笔记
     * @param searchNoteReqVO
     * @return
     */
    PageResponse<SearchNoteRspVO> searchNote(SearchNoteReqVO searchNoteReqVO);

    /**
     * 重建笔记文档
     * @param rebuildNoteDocumentReqDTO
     * @return
     */
    Response<Long> rebuildDocument(RebuildNoteDocumentReqDTO rebuildNoteDocumentReqDTO);
}
