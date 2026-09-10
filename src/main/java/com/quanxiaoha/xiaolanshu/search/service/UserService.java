package com.quanxiaoha.xiaolanshu.search.service;

import com.quanxiaoha.framework.common.response.PageResponse;
import com.quanxiaoha.xiaolanshu.search.model.vo.SearchUserReqVO;
import com.quanxiaoha.xiaolanshu.search.model.vo.SearchUserRspVO;

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
}

