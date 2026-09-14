package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.SegmentIDGenImpl;

public interface SegmentService {
    Result getId(String key);

    SegmentIDGenImpl getIdGen();
}
