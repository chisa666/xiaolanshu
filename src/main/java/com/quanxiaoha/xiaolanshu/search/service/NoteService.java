package com.quanxiaoha.xiaolanshu.search.service;

import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.xiaolanshu.search.model.vo.SearchNoteReqVO;
import com.quanxiaoha.xiaolanshu.search.model.vo.SearchNoteRspVO;

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
}

