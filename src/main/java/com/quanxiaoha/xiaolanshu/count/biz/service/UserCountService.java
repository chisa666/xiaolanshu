package com.quanxiaoha.xiaolanshu.count.biz.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdReqDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdRspDTO;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 用户计数业务
 **/
public interface UserCountService {

    /**
     * 查询用户相关计数
     * @param findUserCountsByIdReqDTO
     * @return
     */
    Response<FindUserCountsByIdRspDTO> findUserCountData(FindUserCountsByIdReqDTO findUserCountsByIdReqDTO);
}

