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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NoteCollectCountShardingXxlJob {
    @Resource private SelectMapper selectMapper;
    @Resource private UpdateMapper updateMapper;
    @Resource private DeleteMapper deleteMapper;
    @Resource private RedisTemplate<String, Object> redisTemplate;
    @Resource private SearchRpcService searchRpcService;

    @XxlJob("noteCollectCountShardingJobHandler")
    public void noteCollectCountShardingJobHandler() {
        int shardIndex = XxlJobHelper.getShardIndex();
        String suffix = TableConstants.buildTableNameSuffix(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")), shardIndex);
        int processed = 0;
        for (;;) {
            List<Long> ids = selectMapper.selectBatchFromDataAlignNoteCollectCountTempTable(suffix, 1000);
            if (CollUtil.isEmpty(ids)) break;
            ids.forEach(id -> {
                int total = selectMapper.selectCountFromNoteCollectionTableByUserId(id);
                if (updateMapper.updateNoteCollectTotalByUserId(id, total) > 0) {
                    String key = RedisKeyConstants.buildCountNoteKey(id);
                    if (redisTemplate.hasKey(key)) redisTemplate.opsForHash().put(key, RedisKeyConstants.FIELD_COLLECT_TOTAL, total);
                }
                searchRpcService.rebuildNoteDocument(id);
            });
            deleteMapper.batchDeleteDataAlignNoteCollectCountTempTable(suffix, ids);
            processed += ids.size();
        }
        XxlJobHelper.log("结束笔记收藏数对齐，共对齐记录数：{}", processed);
    }
}
