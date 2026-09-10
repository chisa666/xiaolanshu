package com.quanxiaoha.xiaolanshu.kv.api;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.kv.constant.ApiConstants;
import com.quanxiaoha.xiaolanshu.kv.dto.req.AddNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.BatchAddCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.BatchFindCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.DeleteCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.FindCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.FindNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.rsp.FindCommentContentRspDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.rsp.FindNoteContentRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: K-V 键值存储 Feign 接口
 **/
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface KeyValueFeignApi {

    String PREFIX = "/kv";

    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(@RequestBody AddNoteContentReqDTO addNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/find")
    Response<FindNoteContentRspDTO> findNoteContent(@RequestBody FindNoteContentReqDTO findNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/comment/content/batchAdd")
    Response<?> batchAddCommentContent(@RequestBody BatchAddCommentContentReqDTO req);

    @PostMapping(value = PREFIX + "/note/content/delete")
    Response<?> deleteNoteContent(@RequestBody DeleteNoteContentReqDTO req);

    @PostMapping(value = PREFIX + "/comment/content/batchFind")
    Response<List<FindCommentContentRspDTO>> batchFindCommentContent(@RequestBody BatchFindCommentContentReqDTO req);

    @PostMapping(value = PREFIX + "/comment/content/delete")
    Response<?> deleteCommentContent(@RequestBody DeleteCommentContentReqDTO req);

}

