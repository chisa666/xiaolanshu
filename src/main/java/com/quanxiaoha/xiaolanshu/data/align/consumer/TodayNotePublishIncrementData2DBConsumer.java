package com.quanxiaoha.xiaolanshu.data.align.consumer;

import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.data.align.constant.MQConstants;
import com.quanxiaoha.xiaolanshu.data.align.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.data.align.constant.TableConstants;
import com.quanxiaoha.xiaolanshu.data.align.domain.mapper.InsertMapper;
import com.quanxiaoha.xiaolanshu.data.align.model.dto.NoteOperateMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 日增量数据落库：笔记发布、删除
 **/
@Component
@RocketMQMessageListener(consumerGroup = "xiaolanshu_group_data_align_" + MQConstants.TOPIC_NOTE_OPERATE, // Group 组
        topic = MQConstants.TOPIC_NOTE_OPERATE // 主题 Topic
        )
@Slf4j
public class TodayNotePublishIncrementData2DBConsumer implements RocketMQListener<String> {

    @Resource private RedisTemplate<String, Object> redisTemplate;
    @Resource private InsertMapper insertMapper;
    @Value("${table.shards}") private int tableShards;

    @Override
    public void onMessage(String body) {
        log.info("## TodayNotePublishIncrementData2DBConsumer 消费到了 MQ: {}", body);

        NoteOperateMqDTO dto = JsonUtils.parseObject(body, NoteOperateMqDTO.class);
        if (Objects.isNull(dto) || dto.getCreatorId() == null) return;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = RedisKeyConstants.buildBloomUserNoteOperateListKey(date);
        DefaultRedisScript<Long> check = new DefaultRedisScript<>();
        check.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/bloom_today_user_note_publish_check.lua")));
        check.setResultType(Long.class);
        if (Objects.equals(redisTemplate.execute(check, Collections.singletonList(key), dto.getCreatorId()), 0L)) {
            insertMapper.insert2DataAlignUserNotePublishCountTempTable(TableConstants.buildTableNameSuffix(date, dto.getCreatorId() % tableShards), dto.getCreatorId());
            redisTemplate.execute(RedisScript.of("return redis.call('BF.ADD', KEYS[1], ARGV[1])", Long.class), Collections.singletonList(key), dto.getCreatorId());
        }
    }
}

