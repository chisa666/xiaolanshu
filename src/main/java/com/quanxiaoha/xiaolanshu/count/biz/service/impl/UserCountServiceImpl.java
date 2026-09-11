package com.quanxiaoha.xiaolanshu.count.biz.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.google.common.collect.Maps;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.count.biz.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.count.biz.domain.dataobject.UserCountDO;
import com.quanxiaoha.xiaolanshu.count.biz.domain.mapper.UserCountDOMapper;
import com.quanxiaoha.xiaolanshu.count.biz.service.UserCountService;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdReqDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 查询用户相关计数
     *
     * @param findUserCountsByIdReqDTO
     * @return
     */
    @Override
    @SentinelResource(value = "findUserCountData", blockHandler = "blockHandler4findUserCountData")
    public Response<FindUserCountsByIdRspDTO> findUserCountData(FindUserCountsByIdReqDTO findUserCountsByIdReqDTO) {
        Long userId = findUserCountsByIdReqDTO.getUserId();
        FindUserCountsByIdRspDTO response = FindUserCountsByIdRspDTO.builder().userId(userId).build();

        String hashKey = RedisKeyConstants.buildCountUserKey(userId);
        List<Object> counts = redisTemplate.opsForHash().multiGet(hashKey, List.of(
                RedisKeyConstants.FIELD_COLLECT_TOTAL,
                RedisKeyConstants.FIELD_FANS_TOTAL,
                RedisKeyConstants.FIELD_NOTE_TOTAL,
                RedisKeyConstants.FIELD_FOLLOWING_TOTAL,
                RedisKeyConstants.FIELD_LIKE_TOTAL));
        if (counts == null || counts.size() < 5) {
            counts = Collections.nCopies(5, null);
        }

        Object collectTotal = counts.get(0);
        Object fansTotal = counts.get(1);
        Object noteTotal = counts.get(2);
        Object followingTotal = counts.get(3);
        Object likeTotal = counts.get(4);

        response.setCollectTotal(toLongOrZero(collectTotal));
        response.setFansTotal(toLongOrZero(fansTotal));
        response.setNoteTotal(toLongOrZero(noteTotal));
        response.setFollowingTotal(toLongOrZero(followingTotal));
        response.setLikeTotal(toLongOrZero(likeTotal));

        if (counts.stream().anyMatch(Objects::isNull)) {
            UserCountDO userCountDO = userCountDOMapper.selectByUserId(userId);
            if (userCountDO != null) {
                if (collectTotal == null) response.setCollectTotal(valueOrZero(userCountDO.getCollectTotal()));
                if (fansTotal == null) response.setFansTotal(valueOrZero(userCountDO.getFansTotal()));
                if (noteTotal == null) response.setNoteTotal(valueOrZero(userCountDO.getNoteTotal()));
                if (followingTotal == null) response.setFollowingTotal(valueOrZero(userCountDO.getFollowingTotal()));
                if (likeTotal == null) response.setLikeTotal(valueOrZero(userCountDO.getLikeTotal()));
            }
            syncHashCount2Redis(hashKey, userCountDO, collectTotal, fansTotal, noteTotal, followingTotal, likeTotal);
        }

        return Response.success(response);
    }

    /**
     * Sentinel 限流/降级处理。
     */
    public Response<FindUserCountsByIdRspDTO> blockHandler4findUserCountData(
            FindUserCountsByIdReqDTO req, BlockException exception) {
        log.warn("findUserCountData limited: {}", JsonUtils.toJsonString(req));
        return Response.success(FindUserCountsByIdRspDTO.builder()
                .userId(req.getUserId())
                .collectTotal(0L)
                .fansTotal(0L)
                .followingTotal(0L)
                .likeTotal(0L)
                .noteTotal(0L)
                .build());
    }

    private Long toLongOrZero(Object value) {
        return value == null ? 0L : Long.parseLong(String.valueOf(value));
    }

    private Long valueOrZero(Long value) {
        return value == null ? 0L : value;
    }

    /**
     * 仅回填缺失字段，并为缓存设置随机过期时间，避免同一时刻集中失效。
     */
    private void syncHashCount2Redis(String hashKey, UserCountDO userCountDO,
                                     Object collectTotal, Object fansTotal, Object noteTotal,
                                     Object followingTotal, Object likeTotal) {
        if (userCountDO == null) {
            return;
        }

        threadPoolTaskExecutor.submit(() -> {
            Map<String, Long> values = Maps.newHashMap();
            if (collectTotal == null) values.put(RedisKeyConstants.FIELD_COLLECT_TOTAL, valueOrZero(userCountDO.getCollectTotal()));
            if (fansTotal == null) values.put(RedisKeyConstants.FIELD_FANS_TOTAL, valueOrZero(userCountDO.getFansTotal()));
            if (noteTotal == null) values.put(RedisKeyConstants.FIELD_NOTE_TOTAL, valueOrZero(userCountDO.getNoteTotal()));
            if (followingTotal == null) values.put(RedisKeyConstants.FIELD_FOLLOWING_TOTAL, valueOrZero(userCountDO.getFollowingTotal()));
            if (likeTotal == null) values.put(RedisKeyConstants.FIELD_LIKE_TOTAL, valueOrZero(userCountDO.getLikeTotal()));

            redisTemplate.executePipelined(new SessionCallback<>() {
                @Override
                public Object execute(RedisOperations operations) {
                    operations.opsForHash().putAll(hashKey, values);
                    long expireTime = 60 * 60L + RandomUtil.randomInt(60 * 60);
                    operations.expire(hashKey, expireTime, TimeUnit.SECONDS);
                    return null;
                }
            });
        });
    }
}
