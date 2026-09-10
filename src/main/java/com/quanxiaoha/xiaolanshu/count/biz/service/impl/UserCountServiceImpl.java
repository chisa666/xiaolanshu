package com.quanxiaoha.xiaolanshu.count.biz.service.impl;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.count.biz.domain.dataobject.UserCountDO;
import com.quanxiaoha.xiaolanshu.count.biz.domain.mapper.UserCountDOMapper;
import com.quanxiaoha.xiaolanshu.count.biz.service.UserCountService;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdReqDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 用户计数业务
 **/
@Service
@Slf4j
public class UserCountServiceImpl implements UserCountService {

    @Resource
    private UserCountDOMapper userCountDOMapper;

    /**
     * 查询用户相关计数
     *
     * @param findUserCountsByIdReqDTO
     * @return
     */
    @Override
    public Response<FindUserCountsByIdRspDTO> findUserCountData(FindUserCountsByIdReqDTO findUserCountsByIdReqDTO) {
        // 目标用户 ID
        Long userId = findUserCountsByIdReqDTO.getUserId();

        FindUserCountsByIdRspDTO findUserCountByIdRspDTO = FindUserCountsByIdRspDTO.builder()
                .userId(userId)
                .fansTotal(0L) // 相关计数默认值置为 0
                .noteTotal(0L)
                .followingTotal(0L)
                .likeTotal(0L)
                .collectTotal(0L)
                .build();

        // 从数据库查询该用户的计数
        UserCountDO userCountDO = userCountDOMapper.selectByUserId(userId);

        // 若计数不为空，设置相关计数数据
        if (Objects.nonNull(userCountDO)) {
            findUserCountByIdRspDTO.setCollectTotal(userCountDO.getCollectTotal());
            findUserCountByIdRspDTO.setFansTotal(userCountDO.getFansTotal());
            findUserCountByIdRspDTO.setNoteTotal(userCountDO.getNoteTotal());
            findUserCountByIdRspDTO.setFollowingTotal(userCountDO.getFollowingTotal());
            findUserCountByIdRspDTO.setLikeTotal(userCountDO.getLikeTotal());
        }

        return Response.success(findUserCountByIdRspDTO);
    }
}

