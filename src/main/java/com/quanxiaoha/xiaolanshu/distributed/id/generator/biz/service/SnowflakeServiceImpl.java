package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.config.LeafProperties;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Local Snowflake-compatible generator. The worker id can be configured per node;
 * ZooKeeper can be introduced later without changing the HTTP contract.
 */
@Service
@Slf4j
public class SnowflakeServiceImpl implements SnowflakeService {
    private static final long EPOCH = 1288834974657L;
    private static final long SEQUENCE_MASK = (1L << 12) - 1;
    private static final long MAX_WORKER_ID = (1L << 10) - 1;

    private final boolean enabled;
    private final long workerId;
    private final AtomicLong fallbackSequence = new AtomicLong(1_000_000L);
    private long lastTimestamp = -1L;
    private long sequence;

    public SnowflakeServiceImpl(LeafProperties properties) {
        enabled = properties.isSnowflakeEnabled();
        workerId = Math.max(0, Math.min(MAX_WORKER_ID, properties.getSnowflakeWorkerId()));
        log.info("Snowflake generator enabled={}, workerId={}", enabled, workerId);
    }

    @Override
    public synchronized Result getId(String key) {
        if (key == null || key.isBlank()) {
            return new Result(0, Status.EXCEPTION);
        }
        if (!enabled) {
            return new Result(fallbackSequence.incrementAndGet(), Status.SUCCESS);
        }
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            return new Result(-1, Status.EXCEPTION);
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - EPOCH) << 22) | (workerId << 12) | sequence;
        return new Result(id, Status.SUCCESS);
    }

    private long waitNextMillis(long previousTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= previousTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}