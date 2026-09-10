package com.quanxiaoha.xiaolanshu.data.align.job;

import cn.hutool.core.collection.CollUtil;
import com.quanxiaoha.xiaolanshu.data.align.constant.RedisKeyConstants;
import com.quanxiaoha.xiaolanshu.data.align.constant.TableConstants;
import com.quanxiaoha.xiaolanshu.data.align.domain.mapper.DeleteMapper;
import com.quanxiaoha.xiaolanshu.data.align.domain.mapper.SelectMapper;
import com.quanxiaoha.xiaolanshu.data.align.domain.mapper.UpdateMapper;
import com.quanxiaoha.xiaolanshu.data.align.rpc.SearchRpcService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 定时分片广播任务：对当日发生变更的笔记点赞数进行对齐
 **/
@Component
@Slf4j
public class NoteLikeCountShardingXxlJob {

    @Resource
    private SelectMapper selectMapper;
    @Resource
    private UpdateMapper updateMapper;
    @Resource
    private DeleteMapper deleteMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private SearchRpcService searchRpcService;

    /**
     * 分片广播任务
     */
    @XxlJob("noteLikeCountShardingJobHandler")
    public void noteLikeCountShardingJobHandler() throws Exception {
        // 获取分片参数
        // 分片序号
        int shardIndex = XxlJobHelper.getShardIndex();
        // 分片总数
        int shardTotal = XxlJobHelper.getShardTotal();

        XxlJobHelper.log("=================> 开始定时分片广播任务：对当日发生变更的笔记点赞数进行对齐");
        XxlJobHelper.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        String date = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String tableNameSuffix = TableConstants.buildTableNameSuffix(date, shardIndex);
        int processedTotal = 0;
        for (;;) {
            List<Long> noteIds = selectMapper.selectBatchFromDataAlignNoteLikeCountTempTable(tableNameSuffix, 1000);
            if (CollUtil.isEmpty(noteIds)) break;
            noteIds.forEach(noteId -> {
                int likeTotal = selectMapper.selectCountFromNoteLikeTableByUserId(noteId);
                int count = updateMapper.updateNoteLikeTotalByUserId(noteId, likeTotal);
                if (count > 0) {
                    String redisKey = RedisKeyConstants.buildCountNoteKey(noteId);
                    if (redisTemplate.hasKey(redisKey)) {
                        redisTemplate.opsForHash().put(redisKey, RedisKeyConstants.FIELD_LIKE_TOTAL, likeTotal);
                    }
                }
                searchRpcService.rebuildNoteDocument(noteId);
            });
            deleteMapper.batchDeleteDataAlignNoteLikeCountTempTable(tableNameSuffix, noteIds);
            processedTotal += noteIds.size();
        }
        XxlJobHelper.log("=================> 结束定时分片广播任务：对当日发生变更的笔记点赞数进行对齐，共对齐记录数：{}", processedTotal);
    }

}

