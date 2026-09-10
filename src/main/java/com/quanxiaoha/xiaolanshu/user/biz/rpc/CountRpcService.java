package com.quanxiaoha.xiaolanshu.user.biz.rpc;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.count.api.CountFeignApi;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdReqDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 计数服务
 **/
@Component
public class CountRpcService {

    @Resource
    private CountFeignApi countFeignApi;

    /**
     * 查询用户计数信息
     * @param userId
     * @return
     */
    public FindUserCountsByIdRspDTO findUserCountById(Long userId) {
        FindUserCountsByIdReqDTO findUserCountsByIdReqDTO = new FindUserCountsByIdReqDTO();
        findUserCountsByIdReqDTO.setUserId(userId);

        Response<FindUserCountsByIdRspDTO> response = countFeignApi.findUserCount(findUserCountsByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

}

