package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.config.LeafProperties;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Status;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.dao.IDAllocDao;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.dao.impl.IDAllocDaoImpl;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.model.LeafAlloc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Leaf segment allocator with a local fallback when the optional table is disabled.
 */
@Service
@Slf4j
public class SegmentServiceImpl implements SegmentService {
    private static final long FALLBACK_START = 1_000_000L;

    private final AtomicLong fallbackSequence = new AtomicLong(FALLBACK_START);
    private final Map<String, SegmentRange> ranges = new ConcurrentHashMap<>();
    private volatile IDAllocDao dao;

    public SegmentServiceImpl(LeafProperties properties, ObjectProvider<DataSource> dataSources) {
        if (properties.isSegmentEnabled()) {
            DataSource dataSource = dataSources.getIfAvailable();
            if (dataSource == null) {
                log.warn("Leaf segment enabled but no DataSource is available; using local fallback");
            } else {
                try {
                    IDAllocDao candidate = new IDAllocDaoImpl(dataSource);
                    candidate.getAllTags();
                    dao = candidate;
                    log.info("Leaf segment allocator initialized from leaf_alloc");
                } catch (Exception ex) {
                    log.warn("Leaf segment initialization failed; using local fallback", ex);
                }
            }
        }
    }

    @Override
    public Result getId(String key) {
        if (key == null || key.isBlank()) {
            return new Result(0, Status.EXCEPTION);
        }
        IDAllocDao activeDao = dao;
        if (activeDao == null) {
            return new Result(fallbackSequence.incrementAndGet(), Status.SUCCESS);
        }
        try {
            return new Result(nextFromDatabase(activeDao, key), Status.SUCCESS);
        } catch (Exception ex) {
            log.error("Leaf segment allocation failed for key {}", key, ex);
            return new Result(-1, Status.EXCEPTION);
        }
    }

    private synchronized long nextFromDatabase(IDAllocDao activeDao, String key) {
        SegmentRange range = ranges.get(key);
        if (range == null || !range.hasNext()) {
            LeafAlloc allocation = activeDao.updateMaxIdAndGetLeafAlloc(key);
            if (allocation == null || allocation.getStep() <= 0) {
                throw new IllegalStateException("leaf_alloc 中不存在有效的业务标识: " + key);
            }
            long start = allocation.getMaxId() - allocation.getStep() + 1;
            range = new SegmentRange(new AtomicLong(start), allocation.getMaxId());
            ranges.put(key, range);
        }
        return range.next().getAndIncrement();
    }

    private record SegmentRange(AtomicLong next, long max) {
        boolean hasNext() {
            return next.get() <= max;
        }
    }
}