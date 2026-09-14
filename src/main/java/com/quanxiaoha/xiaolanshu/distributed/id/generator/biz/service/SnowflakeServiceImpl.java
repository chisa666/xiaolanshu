package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.config.LeafProperties;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.IDGen;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.ZeroIDGen;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.snowflake.SnowflakeIDGenImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Provides Leaf snowflake IDs with optional ZooKeeper worker allocation.
 */
@Service("SnowflakeService")
@Slf4j
public class SnowflakeServiceImpl implements SnowflakeService {
    private final IDGen idGen;

    public SnowflakeServiceImpl(LeafProperties properties) {
        if (!properties.isSnowflakeEnabled()) {
            idGen = new ZeroIDGen();
            log.info("Snowflake generator disabled");
            return;
        }

        IDGen selected;
        if (properties.isSnowflakeZookeeperEnabled()
                && properties.getSnowflakeZkAddress() != null
                && !properties.getSnowflakeZkAddress().isBlank()) {
            try {
                selected = new SnowflakeIDGenImpl(properties.getSnowflakeZkAddress(), properties.getSnowflakePort());
                log.info("Snowflake generator initialized with ZooKeeper workerId");
            } catch (RuntimeException ex) {
                log.warn("ZooKeeper workerId initialization failed; using configured local workerId", ex);
                selected = new SnowflakeIDGenImpl(properties.getSnowflakeWorkerId());
            }
        } else {
            selected = new SnowflakeIDGenImpl(properties.getSnowflakeWorkerId());
            log.info("Snowflake generator initialized with local workerId={}", properties.getSnowflakeWorkerId());
        }
        idGen = selected;
    }

    @Override
    public Result getId(String key) {
        return idGen.get(key);
    }
}