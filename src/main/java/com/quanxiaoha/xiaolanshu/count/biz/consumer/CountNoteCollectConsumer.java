package com.quanxiaoha.xiaolanshu.count.biz.consumer;

import com.github.phantomthief.collection.BufferTrigger;
import com.google.common.collect.Lists;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.count.biz.constant.MQConstants;
import com.quanxiaoha.xiaolanshu.count.biz.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.count.biz.enums.CollectUnCollectNoteTypeEnum;
import com.quanxiaoha.xiaolanshu.count.biz.model.dto.AggregationCountCollectUnCollectNoteMqDTO;
import com.quanxiaoha.xiaolanshu.count.biz.model.dto.CountCollectUnCollectNoteMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 计数: 笔记收藏数
 **/
@Component
@RocketMQMessageListener(consumerGroup = "xiaolanshu_group_" + MQConstants.TOPIC_COUNT_NOTE_COLLECT,
        topic = MQConstants.TOPIC_COUNT_NOTE_COLLECT)
@Slf4j
public class CountNoteCollectConsumer implements RocketMQListener<String> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final BufferTrigger<String> bufferTrigger = BufferTrigger.<String>batchBlocking()
            .bufferSize(50000)
            .batchSize(1000)
            .linger(Duration.ofSeconds(1))
            .setConsumerEx(this::consumeMessage)
            .build();

    @Override
    public void onMessage(String body) {
        if (body != null && !body.isBlank()) {
            bufferTrigger.enqueue(body);
        }
    }

    private void consumeMessage(List<String> bodies) {
        log.info("==> 【笔记收藏数】聚合消息, size: {}", bodies.size());

        List<CountCollectUnCollectNoteMqDTO> messages = bodies.stream()
                .map(body -> JsonUtils.parseObject(body, CountCollectUnCollectNoteMqDTO.class))
                .filter(Objects::nonNull)
                .toList();

        Map<Long, List<CountCollectUnCollectNoteMqDTO>> grouped = messages.stream()
                .filter(item -> item.getNoteId() != null)
                .collect(Collectors.groupingBy(CountCollectUnCollectNoteMqDTO::getNoteId));

        List<AggregationCountCollectUnCollectNoteMqDTO> aggregated = Lists.newArrayList();
        for (Map.Entry<Long, List<CountCollectUnCollectNoteMqDTO>> entry : grouped.entrySet()) {
            Long creatorId = null;
            int count = 0;
            for (CountCollectUnCollectNoteMqDTO item : entry.getValue()) {
                creatorId = item.getNoteCreatorId();
                CollectUnCollectNoteTypeEnum type = CollectUnCollectNoteTypeEnum.valueOf(item.getType());
                if (type == CollectUnCollectNoteTypeEnum.COLLECT) {
                    count++;
                } else if (type == CollectUnCollectNoteTypeEnum.UN_COLLECT) {
                    count--;
                }
            }
            aggregated.add(AggregationCountCollectUnCollectNoteMqDTO.builder()
                    .noteId(entry.getKey())
                    .creatorId(creatorId)
                    .count(count)
                    .build());
        }

        log.info("## 【笔记收藏数】聚合后的计数数据: {}", JsonUtils.toJsonString(aggregated));

        aggregated.forEach(item -> {
            if (item.getCount() == 0) {
                return;
            }

            String noteKey = RedisKeyConstants.buildCountNoteKey(item.getNoteId());
            if (Boolean.TRUE.equals(redisTemplate.hasKey(noteKey))) {
                redisTemplate.opsForHash().increment(noteKey, RedisKeyConstants.FIELD_COLLECT_TOTAL, item.getCount());
            }

            if (item.getCreatorId() != null) {
                String userKey = RedisKeyConstants.buildCountUserKey(item.getCreatorId());
                if (Boolean.TRUE.equals(redisTemplate.hasKey(userKey))) {
                    redisTemplate.opsForHash().increment(userKey, RedisKeyConstants.FIELD_COLLECT_TOTAL, item.getCount());
                }
            }
        });

        if (aggregated.isEmpty()) {
            return;
        }

        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(aggregated)).build();
        rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_NOTE_COLLECT_2_DB, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> 【计数服务：笔记收藏数入库】MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> 【计数服务：笔记收藏数入库】MQ 发送异常: ", throwable);
            }
        });
    }
}
