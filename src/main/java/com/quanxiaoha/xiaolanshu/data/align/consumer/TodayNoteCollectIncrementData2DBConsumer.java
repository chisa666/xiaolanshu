package com.quanxiaoha.xiaolanshu.data.align.consumer;

import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.data.align.constant.MQConstants;
import com.quanxiaoha.xiaolanshu.data.align.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.data.align.constant.TableConstants;
import com.quanxiaoha.xiaolanshu.data.align.domain.mapper.InsertMapper;
import com.quanxiaoha.xiaolanshu.data.align.model.dto.CollectUnCollectNoteMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 日增量数据落库：笔记收藏、取消收藏
 **/
@Component
@RocketMQMessageListener(consumerGroup = "xiaolanshu_group_data_align_" + MQConstants.TOPIC_COUNT_NOTE_COLLECT, // Group 组
        topic = MQConstants.TOPIC_COUNT_NOTE_COLLECT // 主题 Topic
        )
@Slf4j
public class TodayNoteCollectIncrementData2DBConsumer implements RocketMQListener<String> {

    @Resource private RedisTemplate<String, Object> redisTemplate;
    @Resource private InsertMapper insertMapper;
    @Value("${table.shards}") private int tableShards;

    @Override
    public void onMessage(String body) {
        log.info("## TodayNoteCollectIncrementData2DBConsumer 消费到了 MQ: {}", body);

        CollectUnCollectNoteMqDTO dto = JsonUtils.parseObject(body, CollectUnCollectNoteMqDTO.class);
        if (Objects.isNull(dto) || dto.getNoteId() == null || dto.getNoteCreatorId() == null) return;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        DefaultRedisScript<Long> check = new DefaultRedisScript<>();
        check.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/bloom_today_note_collect_check.lua")));
        check.setResultType(Long.class);
        RedisScript<Long> add = RedisScript.of("return redis.call('BF.ADD', KEYS[1], ARGV[1])", Long.class);

        String noteKey = RedisKeyConstants.buildBloomUserNoteCollectNoteIdListKey(date);
        if (Objects.equals(redisTemplate.execute(check, Collections.singletonList(noteKey), dto.getNoteId()), 0L)) {
            insertMapper.insert2DataAlignNoteCollectCountTempTable(TableConstants.buildTableNameSuffix(date, dto.getNoteId() % tableShards), dto.getNoteId());
            redisTemplate.execute(add, Collections.singletonList(noteKey), dto.getNoteId());
        }
        String userKey = RedisKeyConstants.buildBloomUserNoteCollectUserIdListKey(date);
        if (Objects.equals(redisTemplate.execute(check, Collections.singletonList(userKey), dto.getNoteCreatorId()), 0L)) {
            insertMapper.insert2DataAlignUserCollectCountTempTable(TableConstants.buildTableNameSuffix(date, dto.getNoteCreatorId() % tableShards), dto.getNoteCreatorId());
            redisTemplate.execute(add, Collections.singletonList(userKey), dto.getNoteCreatorId());
        }
    }
}

