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
public class NotePublishCountShardingXxlJob {
    @Resource private SelectMapper selectMapper;
    @Resource private UpdateMapper updateMapper;
    @Resource private DeleteMapper deleteMapper;
    @Resource private RedisTemplate<String, Object> redisTemplate;
    @Resource private SearchRpcService searchRpcService;

    @XxlJob("notePublishCountShardingJobHandler")
    public void notePublishCountShardingJobHandler() {
        int shardIndex = XxlJobHelper.getShardIndex();
        String suffix = TableConstants.buildTableNameSuffix(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")), shardIndex);
        int processed = 0;
        for (;;) {
            List<Long> ids = selectMapper.selectBatchFromDataAlignNotePublishCountTempTable(suffix, 1000);
            if (CollUtil.isEmpty(ids)) break;
            ids.forEach(id -> {
                int total = selectMapper.selectCountFromNoteTableByUserId(id);
                if (updateMapper.updateUserNoteTotalByUserId(id, total) > 0) {
                    String key = RedisKeyConstants.buildCountUserKey(id);
                    if (redisTemplate.hasKey(key)) redisTemplate.opsForHash().put(key, RedisKeyConstants.FIELD_NOTE_TOTAL, total);
                }
                searchRpcService.rebuildUserDocument(id);
            });
            deleteMapper.batchDeleteDataAlignNotePublishCountTempTable(suffix, ids);
            processed += ids.size();
        }
        XxlJobHelper.log("结束笔记发布数对齐，共对齐记录数：{}", processed);
    }
}
