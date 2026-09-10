package com.quanxiaoha.xiaolanshu.count.api;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.count.constant.ApiConstants;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdReqDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindUserCountsByIdRspDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindNoteCountsByIdsReqDTO;
import com.quanxiaoha.xiaolanshu.count.dto.FindNoteCountsByIdRspDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 计数服务 Feign 接口
 **/
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface CountFeignApi {

    String PREFIX = "/count";

    /**
     * 查询用户计数
     *
     * @param findUserCountsByIdReqDTO
     * @return
     */
    @PostMapping(value = PREFIX + "/user/data")
    Response<FindUserCountsByIdRspDTO> findUserCount(@RequestBody FindUserCountsByIdReqDTO findUserCountsByIdReqDTO);

    @PostMapping(value = PREFIX + "/notes/data")
    Response<List<FindNoteCountsByIdRspDTO>> findNotesCount(@RequestBody FindNoteCountsByIdsReqDTO req);

}

