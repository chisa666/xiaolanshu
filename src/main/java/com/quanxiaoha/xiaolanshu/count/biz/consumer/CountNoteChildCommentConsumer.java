package com.quanxiaoha.xiaolanshu.count.biz.consumer;

import cn.hutool.core.collection.CollUtil;
import com.github.phantomthief.collection.BufferTrigger;
import com.google.common.collect.Lists;
import com.quanxiaoha.framework.common.util.JsonUtils;
import com.quanxiaoha.xiaolanshu.count.biz.constant.MQConstants;
import com.quanxiaoha.xiaolanshu.count.biz.domain.mapper.CommentDOMapper;
import com.quanxiaoha.xiaolanshu.count.biz.enums.CommentLevelEnum;
import com.quanxiaoha.xiaolanshu.count.biz.model.dto.CountPublishCommentMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 计数: 笔记二级评论数
 **/
@Component
@RocketMQMessageListener(consumerGroup = "xiaolanshu_group_child_comment_total" + MQConstants.TOPIC_COUNT_NOTE_COMMENT, // Group 组
        topic = MQConstants.TOPIC_COUNT_NOTE_COMMENT // 主题 Topic
        )
@Slf4j
public class CountNoteChildCommentConsumer implements RocketMQListener<String> {

    @Resource
    private CommentDOMapper commentDOMapper;

    @Override
    public void onMessage(String body) { consumeMessage(List.of(body)); }

    private void consumeMessage(List<String> bodys) {
        log.info("==> 【笔记二级评论数】聚合消息, size: {}", bodys.size());
        log.info("==> 【笔记二级评论数】聚合消息, {}", JsonUtils.toJsonString(bodys));

        // 将聚合后的消息体 Json 转 List<CountPublishCommentMqDTO>
        List<CountPublishCommentMqDTO> countPublishCommentMqDTOList = Lists.newArrayList();
        bodys.forEach(body -> {
            try {
                List<CountPublishCommentMqDTO> list = JsonUtils.parseList(body, CountPublishCommentMqDTO.class);
                countPublishCommentMqDTOList.addAll(list);
            } catch (Exception e) {
                log.error("", e);
            }
        });

        // 过滤出二级评论，并按 parent_id 分组
        Map<Long, List<CountPublishCommentMqDTO>> groupMap = countPublishCommentMqDTOList.stream()
                .filter(commentMqDTO -> Objects.equals(CommentLevelEnum.TWO.getCode(), commentMqDTO.getLevel()))
                .collect(Collectors.groupingBy(CountPublishCommentMqDTO::getParentId)); // 按 parent_id 分组

        // 若无二级评论，则直接 return
        if (CollUtil.isEmpty(groupMap)) return;

        // 循环分组字典
        for (Map.Entry<Long, List<CountPublishCommentMqDTO>> entry : groupMap.entrySet()) {
            // 一级评论 ID
            Long parentId = entry.getKey();
            // 评论数
            int count = CollUtil.size(entry.getValue());

            // 更新一级评论的下级评论总数，进行累加操作
            commentDOMapper.updateChildCommentTotal(parentId, count);
        }
    }
}

