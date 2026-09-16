package com.quanxiaoha.xiaolanshu.kv.biz.controller;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.kv.biz.service.NoteContentService;
import com.quanxiaoha.xiaolanshu.kv.dto.req.AddNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.FindNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.rsp.FindNoteContentRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 笔记内容
 **/
@RestController
@RequestMapping("/kv")
@Slf4j
public class NoteContentController {

    @Resource
    private NoteContentService noteContentService;

    @PostMapping(value = "/note/content/add")
    public Response<?> addNoteContent(@Validated @RequestBody AddNoteContentReqDTO addNoteContentReqDTO) {
        return noteContentService.addNoteContent(addNoteContentReqDTO);
    }

    @PostMapping(value = "/note/content/find")
    public Response<FindNoteContentRspDTO> findNoteContent(@Validated @RequestBody FindNoteContentReqDTO req) {
        return noteContentService.findNoteContent(req);
    }

    @PostMapping(value = "/note/content/delete")
    public Response<?> deleteNoteContent(@Validated @RequestBody DeleteNoteContentReqDTO req) {
        return noteContentService.deleteNoteContent(req);
    }

}

