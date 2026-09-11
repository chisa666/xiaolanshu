package com.quanxiaoha.xiaolanshu.note.biz.rpc;

import cn.hutool.core.collection.CollUtil;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.count.api.CountFeignApi;
import com.quanxiaoha.xiaolanshu.count.dto.FindNoteCountsByIdRspDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindNoteCountsByIdsReqDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 计数服务
 **/
@Component
public class CountRpcService {

    @Resource
    private CountFeignApi countFeignApi;

    /**
     * 批量查询笔记计数
     *
     * @param noteIds
     * @return
     */
    public List<FindNoteCountsByIdRspDTO> findByNoteIds(List<Long> noteIds) {
        FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO = new FindNoteCountsByIdsReqDTO();
        findNoteCountsByIdsReqDTO.setNoteIds(noteIds);

        Response<List<FindNoteCountsByIdRspDTO>> response = countFeignApi.findNotesCount(findNoteCountsByIdsReqDTO);

        if (Objects.isNull(response) || !response.isSuccess() || Objects.isNull(response.getData()) || CollUtil.isEmpty(response.getData())) {
            return null;
        }

        return response.getData();
    }

}

