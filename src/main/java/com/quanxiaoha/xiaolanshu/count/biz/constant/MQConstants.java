package com.quanxiaoha.xiaolanshu.count.biz.constant;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: MQ 常量
 **/
public interface MQConstants {

    /**
     * Topic: 关注数计数
     */
    String TOPIC_COUNT_FOLLOWING = "CountFollowingTopic";

    /**
     * Topic: 粉丝数计数
     */
    String TOPIC_COUNT_FANS = "CountFansTopic";

    String TOPIC_COUNT_FOLLOWING_2_DB = "CountFollowing2DBTopic";

    String TOPIC_COUNT_FANS_2_DB = "CountFans2DBTopic";

    String TOPIC_COUNT_NOTE_LIKE = "CountNoteLikeTopic";

    String TOPIC_LIKE_OR_UNLIKE = "LikeUnlikeTopic";

    String TOPIC_COUNT_NOTE_LIKE_2_DB = "CountNoteLike2DBTTopic";

    String TOPIC_COUNT_NOTE_COLLECT = "CountNoteCollectTopic";

    String TOPIC_COUNT_NOTE_COLLECT_2_DB = "CountNoteCollect2DBTTopic";

    String TOPIC_COUNT_NOTE_COMMENT = "CountNoteCommentTopic";

    String TOPIC_COMMENT_HEAT_UPDATE = "CommentHeatUpdateTopic";

    String TOPIC_COMMENT_LIKE_OR_UNLIKE = "CommentLikeUnlikeTopic";

    String TOPIC_COUNT_COMMENT_LIKE_2_DB = "CountCommentLike2DBTTopic";

    String TOPIC_NOTE_OPERATE = "NoteOperateTopic";

    String TAG_NOTE_PUBLISH = "publishNote";

    String TAG_NOTE_DELETE = "deleteNote";

}

