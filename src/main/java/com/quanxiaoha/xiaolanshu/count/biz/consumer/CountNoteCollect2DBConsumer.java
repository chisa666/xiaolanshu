package com.quanxiaoha.xiaolanshu.count.biz.consumer;

import cn.hutool.core.collection.CollUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.count.biz.constant.MQConstants;
import com.quanxiaoha.xiaolanshu.count.biz.domain.mapper.NoteCountDOMapper;
import com.quanxiaoha.xiaolanshu.count.biz.domain.mapper.UserCountDOMapper;
import com.quanxiaoha.xiaolanshu.count.biz.model.dto.AggregationCountCollectUnCollectNoteMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 计数: 笔记收藏数落库
 **/
@Component
@RocketMQMessageListener(consumerGroup = "xiaolanshu_group_" + MQConstants.TOPIC_COUNT_NOTE_COLLECT_2_DB,
        topic = MQConstants.TOPIC_COUNT_NOTE_COLLECT_2_DB)
@Slf4j
public class CountNoteCollect2DBConsumer implements RocketMQListener<String> {

    @Resource
    private NoteCountDOMapper noteCountDOMapper;
    @Resource
    private UserCountDOMapper userCountDOMapper;
    @Resource
    private TransactionTemplate transactionTemplate;

    private final RateLimiter rateLimiter = RateLimiter.create(5000);

    @Override
    public void onMessage(String body) {
        rateLimiter.acquire();
        log.info("## 消费到了 MQ 【计数: 笔记收藏数入库】, {}...", body);

        List<AggregationCountCollectUnCollectNoteMqDTO> countList;
        try {
            countList = JsonUtils.parseList(body, AggregationCountCollectUnCollectNoteMqDTO.class);
        } catch (Exception e) {
            log.error("## 解析收藏计数 JSON 异常", e);
            return;
        }

        if (CollUtil.isEmpty(countList)) {
            return;
        }

        countList.stream()
                .filter(item -> item.getNoteId() != null && item.getCreatorId() != null && item.getCount() != null)
                .forEach(item -> transactionTemplate.execute(status -> {
                    try {
                        noteCountDOMapper.insertOrUpdateCollectTotalByNoteId(item.getCount(), item.getNoteId());
                        userCountDOMapper.insertOrUpdateCollectTotalByUserId(item.getCount(), item.getCreatorId());
                        return true;
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        log.error("收藏计数落库失败, noteId={}, creatorId={}", item.getNoteId(), item.getCreatorId(), e);
                        return false;
                    }
                }));
    }
}
