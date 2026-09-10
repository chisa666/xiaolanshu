package com.quanxiaoha.xiaolanshu.kv.biz.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.kv.dto.req.BatchAddCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.BatchFindCommentContentReqDTO;
import com.quanxiaoha.xiaolanshu.kv.dto.req.DeleteCommentContentReqDTO;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 评论内容业务
 **/
public interface CommentContentService {

    /**
     * 批量添加评论内容
     * @param batchAddCommentContentReqDTO
     * @return
     */
    Response<?> batchAddCommentContent(BatchAddCommentContentReqDTO batchAddCommentContentReqDTO);

    /**
     * 批量查询评论内容
     * @param batchFindCommentContentReqDTO
     * @return
     */
    Response<?> batchFindCommentContent(BatchFindCommentContentReqDTO batchFindCommentContentReqDTO);

    /**
     * 删除评论内容
     */
    Response<?> deleteCommentContent(DeleteCommentContentReqDTO deleteCommentContentReqDTO);

}

