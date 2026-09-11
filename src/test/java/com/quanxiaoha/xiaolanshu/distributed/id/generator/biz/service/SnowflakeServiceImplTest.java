package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.config.LeafProperties;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Status;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnowflakeServiceImplTest {

    @Test
    void generatesUniqueIdsWithinSameProcess() {
        LeafProperties properties = new LeafProperties();
        properties.setSnowflakeEnabled(true);
        properties.setSnowflakeWorkerId(7);
        SnowflakeServiceImpl service = new SnowflakeServiceImpl(properties);

        Set<Long> ids = new HashSet<>();
        for (int i = 0; i < 5000; i++) {
            var result = service.getId("test");
            assertEquals(Status.SUCCESS, result.getStatus());
            ids.add(result.getId());
        }

        assertEquals(5000, ids.size());
    }

    @Test
    void rejectsBlankKey() {
        LeafProperties properties = new LeafProperties();
        SnowflakeServiceImpl service = new SnowflakeServiceImpl(properties);

        assertTrue(service.getId(" ").getStatus() == Status.EXCEPTION);
    }
}