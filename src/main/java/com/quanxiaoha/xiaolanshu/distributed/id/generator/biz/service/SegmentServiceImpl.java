package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Status;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class SegmentServiceImpl implements SegmentService {
    private final AtomicLong sequence = new AtomicLong(1000000);

    @Override
    public Result getId(String key) {
        return key == null || key.isBlank() ? new Result(0, Status.EXCEPTION)
                : new Result(sequence.incrementAndGet(), Status.SUCCESS);
    }
}
