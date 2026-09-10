package com.quanxiaoha.xiaolanshu.search.biz.service;

import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.dto.RebuildUserDocumentReqDTO;
import com.quanxiaoha.xiaolanshu.search.biz.model.vo.SearchUserReqVO;
import com.quanxiaoha.xiaolanshu.search.biz.model.vo.SearchUserRspVO;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 用户搜索业务
 **/
public interface UserService {

    /**
     * 搜索用户
     * @param searchUserReqVO
     * @return
     */
    PageResponse<SearchUserRspVO> searchUser(SearchUserReqVO searchUserReqVO);

    /**
     * 重建用户文档
     * @param rebuildUserDocumentReqDTO
     * @return
     */
    Response<Long> rebuildDocument(RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO);
}
