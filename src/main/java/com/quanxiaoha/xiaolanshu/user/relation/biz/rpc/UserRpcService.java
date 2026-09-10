package com.quanxiaoha.xiaolanshu.user.relation.biz.rpc;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.user.api.UserFeignApi;
import com.quanxiaoha.xiaolanshu.user.dto.req.FindUserByIdReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.req.FindUsersByIdsReqDTO;
import com.quanxiaoha.xiaolanshu.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.List;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 用户服务
 **/
@Component
public class UserRpcService {

    @Resource
    private UserFeignApi userFeignApi;

    /**
     * 根据用户 ID 查询
     *
     * @param userId
     * @return
     */
    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData())) {
            return null;
        }

        return response.getData();
    }

    public List<FindUserByIdRspDTO> findByIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) return List.of();
        Response<List<FindUserByIdRspDTO>> response = userFeignApi.findByIds(FindUsersByIdsReqDTO.builder().ids(userIds).build());
        return response != null && response.isSuccess() && response.getData() != null ? response.getData() : List.of();
    }

}

