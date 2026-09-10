package com.quanxiaoha.xiaolanshu.count.biz.consumer;

import com.github.phantomthief.collection.BufferTrigger;
import com.google.common.collect.Maps;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.count.biz.constant.MQConstants;
import com.quanxiaoha.xiaolanshu.count.biz.enums.CollectUnCollectNoteTypeEnum;
import com.quanxiaoha.xiaolanshu.count.biz.model.dto.CountCollectUnCollectNoteMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.RedisTemplate;
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
@RocketMQMessageListener(consumerGroup = "xiaolanshu_group_" + MQConstants.TOPIC_COUNT_NOTE_COLLECT, // Group 组
        topic = MQConstants.TOPIC_COUNT_NOTE_COLLECT // 主题 Topic
        )
@Slf4j
public class CountNoteCollectConsumer implements RocketMQListener<String> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void onMessage(String body) { consumeMessage(List.of(body)); }

    private void consumeMessage(List<String> bodys) {
        log.info("==> 【笔记收藏数】聚合消息, size: {}", bodys.size());
        log.info("==> 【笔记收藏数】聚合消息, {}", JsonUtils.toJsonString(bodys));

        // List<String> 转 List<CountCollectUnCollectNoteMqDTO>
        List<CountCollectUnCollectNoteMqDTO> countCollectUnCollectNoteMqDTOS = bodys.stream()
                .map(body -> JsonUtils.parseObject(body, CountCollectUnCollectNoteMqDTO.class)).toList();

        // 按笔记 ID 进行分组
        Map<Long, List<CountCollectUnCollectNoteMqDTO>> groupMap = countCollectUnCollectNoteMqDTOS.stream()
                .collect(Collectors.groupingBy(CountCollectUnCollectNoteMqDTO::getNoteId));

        // 按组汇总数据，统计出最终的计数
        // key 为笔记 ID, value 为最终操作的计数
        Map<Long, Integer> countMap = Maps.newHashMap();

        for (Map.Entry<Long, List<CountCollectUnCollectNoteMqDTO>> entry : groupMap.entrySet()) {
            List<CountCollectUnCollectNoteMqDTO> list = entry.getValue();
            // 最终的计数值，默认为 0
            int finalCount = 0;
            for (CountCollectUnCollectNoteMqDTO countCollectUnCollectNoteMqDTO : list) {
                // 获取操作类型
                Integer type = countCollectUnCollectNoteMqDTO.getType();

                // 根据操作类型，获取对应枚举
                CollectUnCollectNoteTypeEnum collectUnCollectNoteTypeEnum = CollectUnCollectNoteTypeEnum.valueOf(type);

                // 若枚举为空，跳到下一次循环
                if (Objects.isNull(collectUnCollectNoteTypeEnum)) continue;

                switch (collectUnCollectNoteTypeEnum) {
                    case COLLECT -> finalCount += 1; // 如果为收藏操作，点赞数 +1
                    case UN_COLLECT -> finalCount -= 1; // 如果为取消收藏操作，点赞数 -1
                }
            }
            // 将分组后统计出的最终计数，存入 countMap 中
            countMap.put(entry.getKey(), finalCount);
        }

        log.info("## 【笔记收藏数】聚合后的计数数据: {}", JsonUtils.toJsonString(countMap));
    }
}

