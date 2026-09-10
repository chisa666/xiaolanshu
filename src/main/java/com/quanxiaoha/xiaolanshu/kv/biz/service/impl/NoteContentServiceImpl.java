package com.quanxiaoha.xiaolanshu.kv.biz.service.impl;

import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.kv.biz.domain.dataobject.NoteContentDO;
import com.quanxiaoha.xiaolanshu.kv.biz.domain.repository.NoteContentRepository;
import com.quanxiaoha.xiaolanshu.kv.biz.enums.ResponseCodeEnum;
import com.quanxiaoha.xiaolanshu.kv.biz.service.NoteContentService;
import com.quanxiaoha.xiaolanshu.kv.dto.req.AddNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.FindNoteContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.rsp.FindNoteContentRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: Key-Value 业务
 **/
@Service
@Slf4j
public class NoteContentServiceImpl implements NoteContentService {

    @Resource
    private NoteContentRepository noteContentRepository;

    @Override
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO) {
        String uuid = addNoteContentReqDTO.getUuid();
        UUID id = uuid == null || uuid.isBlank() ? UUID.randomUUID() : UUID.fromString(uuid);
        NoteContentDO noteContent = NoteContentDO.builder()
                .id(id)
                .content(addNoteContentReqDTO.getContent())
                .build();
        noteContentRepository.save(noteContent);
        return Response.success();
    }

    /**
     * 查询笔记内容
     *
     * @param findNoteContentReqDTO
     * @return
     */
    @Override
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO) {
        // 笔记 ID
        String noteId = findNoteContentReqDTO.getNoteId();
        // 根据笔记 ID 查询笔记内容
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString(noteId));

        // 若笔记内容不存在
        if (!optional.isPresent()) {
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_NOT_FOUND);
        }

        NoteContentDO noteContentDO = optional.get();
        // 构建返参 DTO
        FindNoteContentRspDTO findNoteContentRspDTO = FindNoteContentRspDTO.builder()
                .noteId(noteContentDO.getId())
                .content(noteContentDO.getContent())
                .build();

        return Response.success(findNoteContentRspDTO);
    }

    @Override
    public Response<?> deleteNoteContent(DeleteNoteContentReqDTO req) {
        noteContentRepository.deleteById(UUID.fromString(req.getUuid()));
        return Response.success();
    }
}

