package com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.service;

import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.config.LeafProperties;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.IDGen;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.Result;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.common.ZeroIDGen;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.SegmentIDGenImpl;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.dao.IDAllocDao;
import com.quanxiaoha.xiaolanshu.distributed.id.generator.biz.core.segment.dao.impl.IDAllocDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Provides Leaf segment IDs and keeps local development usable without MySQL.
 */
@Service("SegmentService")
@Slf4j
public class SegmentServiceImpl implements SegmentService {
    private final IDGen idGen;

    public SegmentServiceImpl(LeafProperties properties, ObjectProvider<DataSource> dataSources) {
        IDGen selected = new ZeroIDGen();
        if (properties.isSegmentEnabled()) {
            DataSource dataSource = dataSources.getIfAvailable();
            if (dataSource == null) {
                log.warn("Leaf segment is enabled but no DataSource is available; using zero generator");
            } else {
                try {
                    IDAllocDao dao = new IDAllocDaoImpl(dataSource);
                    SegmentIDGenImpl segmentGenerator = new SegmentIDGenImpl();
                    segmentGenerator.setDao(dao);
                    if (!segmentGenerator.init()) {
                        throw new IllegalStateException("Leaf segment generator initialization failed");
                    }
                    selected = segmentGenerator;
                    log.info("Leaf segment generator initialized");
                } catch (Exception ex) {
                    log.warn("Leaf segment initialization failed; using zero generator", ex);
                }
            }
        }
        idGen = selected;
    }

    @Override
    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        return idGen instanceof SegmentIDGenImpl segmentGenerator ? segmentGenerator : null;
    }
}