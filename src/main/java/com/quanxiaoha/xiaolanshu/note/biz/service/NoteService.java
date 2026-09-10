package com.quanxiaoha.xiaolanshu.note.biz.service;

import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaolanshu.note.biz.domain.dataobject.UnCollectNoteReqVO;
import com.quanxiaoha.xiaolanshu.note.biz.model.vo.*;

/**
 * 笔记服务
 */
public interface NoteService {

    /**
     * 发布笔记
     * @param publishNoteReqVO
     * @return
     */
    Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);

    /**
     * 笔记详情
     * @param findNoteDetailReqVO
     * @return
     */
    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);

    /**
     * 笔记更新
     * @param updateNoteReqVO
     * @return
     */
    Response<?> updateNote(UpdateNoteReqVO updateNoteReqVO);

    /**
     * 删除本地笔记缓存
     * @param noteId
     */
    void deleteNoteLocalCache(Long noteId);

    /**
     * 删除笔记
     * @param deleteNoteReqVO
     * @return
     */
    Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO);

    /**
     * 笔记可见仅自己可见
     * @param updateNoteVisibleOnlyMeReqVO
     * @return
     */
    Response<?> visibleOnlyMe(UpdateNoteVisibleOnlyMeReqVO updateNoteVisibleOnlyMeReqVO);

    /**
     * 笔记置顶 / 取消置顶
     * @param topNoteReqVO
     * @return
     */
    Response<?> topNote(TopNoteReqVO topNoteReqVO);

    /**
     * 笔记点赞
     * @param likeNoteReqVO
     * @return
     */
    Response<?> likeNote(LikeNoteReqVO likeNoteReqVO);

    /**
     * 取消点赞笔记
     * @param unlikeNoteReqVO
     * @return
     */
    Response<?> unlikeNote(UnlikeNoteReqVO unlikeNoteReqVO);

    /**
     * 收藏笔记
     * @param collectNoteReqVO
     * @return
     */
    Response<?> collectNote(CollectNoteReqVO collectNoteReqVO);

    /**
     * 取消收藏笔记
     * @param unCollectNoteReqVO
     * @return
     */
    Response<?> unCollectNote(UnCollectNoteReqVO unCollectNoteReqVO);

    /**
     * 获取是否点赞、收藏数据
     * @param findNoteIsLikedAndCollectedReqVO
     * @return
     */
    Response<FindNoteIsLikedAndCollectedRspVO> isLikedAndCollectedData(FindNoteIsLikedAndCollectedReqVO findNoteIsLikedAndCollectedReqVO);

    /**
     * 用户主页 - 查询已发布的笔记列表
     * @param findPublishedNoteListReqVO
     * @return
     */
    Response<FindPublishedNoteListRspVO> findPublishedNoteList(FindPublishedNoteListReqVO findPublishedNoteListReqVO);
}


