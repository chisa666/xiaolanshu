package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Status;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.dao.IDAllocDao;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.model.LeafAlloc;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SegmentIDGenImplTest {

    @Test
    void allocatesContinuousUniqueIdsAcrossSegments() {
        SegmentIDGenImpl generator = new SegmentIDGenImpl();
        generator.setDao(new InMemoryAllocDao(5));
        assertTrue(generator.init());

        Set<Long> ids = new TreeSet<>();
        for (int i = 0; i < 25; i++) {
            Result result = generator.get("test");
            assertEquals(Status.SUCCESS, result.getStatus());
            ids.add(result.getId());
        }

        assertEquals(25, ids.size());
        assertEquals(LongStream.rangeClosed(1, 25).boxed().toList(), ids.stream().toList());
    }

    @Test
    void returnsExceptionForUnknownKey() {
        SegmentIDGenImpl generator = new SegmentIDGenImpl();
        generator.setDao(new InMemoryAllocDao(10));
        assertTrue(generator.init());

        assertEquals(Status.EXCEPTION, generator.get("missing").getStatus());
    }

    private static final class InMemoryAllocDao implements IDAllocDao {
        private final int step;
        private final AtomicLong maxId = new AtomicLong(1);

        private InMemoryAllocDao(int step) {
            this.step = step;
        }

        @Override
        public List<LeafAlloc> getAllLeafAllocs() {
            return List.of(allocation(maxId.get()));
        }

        @Override
        public synchronized LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
            return allocation(maxId.addAndGet(step));
        }

        @Override
        public synchronized LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
            return allocation(maxId.addAndGet(leafAlloc.getStep()));
        }

        @Override
        public List<String> getAllTags() {
            return List.of("test");
        }

        private LeafAlloc allocation(long max) {
            LeafAlloc allocation = new LeafAlloc();
            allocation.setKey("test");
            allocation.setMaxId(max);
            allocation.setStep(step);
            return allocation;
        }
    }
}