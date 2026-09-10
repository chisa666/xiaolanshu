package com.quanxiaoha.xiaolanshu.kv.biz.domain.repository;

import com.quanxiaoha.xiaolanshu.kv.biz.domain.dataobject.NoteContentDO;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: TODO
 **/
public interface NoteContentRepository extends CassandraRepository<NoteContentDO, UUID> {

}

