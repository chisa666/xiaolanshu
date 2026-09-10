package com.quanxiaoha.xiaolanshu.data.align.constant;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: TODO
 **/
public class RedisKeyConstants {

    private static final String BLOOM_TODAY_NOTE_COLLECT_NOTE_ID_LIST_KEY = "bloom:dataAlign:note:collect:noteIds";
    private static final String BLOOM_TODAY_NOTE_COLLECT_USER_ID_LIST_KEY = "bloom:dataAlign:note:collect:userIds";
    private static final String BLOOM_TODAY_USER_NOTE_OPERATOR_LIST_KEY = "bloom:dataAlign:user:note:operators:";
    private static final String BLOOM_TODAY_USER_FOLLOW_LIST_KEY = "bloom:dataAlign:user:follows:";
    private static final String BLOOM_TODAY_USER_FANS_LIST_KEY = "bloom:dataAlign:user:fans:";
    private static final String COUNT_USER_KEY_PREFIX = "count:user:";
    private static final String COUNT_NOTE_KEY_PREFIX = "count:note:";
    public static final String FIELD_FOLLOWING_TOTAL = "followingTotal";
    public static final String FIELD_FANS_TOTAL = "fansTotal";
    public static final String FIELD_LIKE_TOTAL = "likeTotal";
    public static final String FIELD_COLLECT_TOTAL = "collectTotal";
    public static final String FIELD_NOTE_TOTAL = "noteTotal";

    /**
     * 布隆过滤器：日增量变更数据，用户笔记点赞，取消点赞（笔记ID） 前缀
     */
    public static final String BLOOM_TODAY_NOTE_LIKE_NOTE_ID_LIST_KEY = "bloom:dataAlign:note:like:noteIds";

    /**
     * 布隆过滤器：日增量变更数据，用户笔记点赞，取消点赞（笔记发布者ID） 前缀
     */
    public static final String BLOOM_TODAY_NOTE_LIKE_USER_ID_LIST_KEY = "bloom:dataAlign:note:like:userIds";

    /**
     * 构建完整的布隆过滤器：日增量变更数据，用户笔记点赞，取消点赞(笔记ID) KEY
     * @param date
     * @return
     */
    public static String buildBloomUserNoteLikeNoteIdListKey(String date) {
        return BLOOM_TODAY_NOTE_LIKE_NOTE_ID_LIST_KEY + date;
    }

    /**
     * 构建完整的布隆过滤器：日增量变更数据，用户笔记点赞，取消点赞(笔记发布者ID) KEY
     * @param date
     * @return
     */
    public static String buildBloomUserNoteLikeUserIdListKey(String date) {
        return BLOOM_TODAY_NOTE_LIKE_USER_ID_LIST_KEY + date;
    }

    public static String buildBloomUserNoteCollectNoteIdListKey(String date) {
        return BLOOM_TODAY_NOTE_COLLECT_NOTE_ID_LIST_KEY + date;
    }

    public static String buildBloomUserNoteCollectUserIdListKey(String date) {
        return BLOOM_TODAY_NOTE_COLLECT_USER_ID_LIST_KEY + date;
    }

    public static String buildBloomUserNoteOperateListKey(String date) {
        return BLOOM_TODAY_USER_NOTE_OPERATOR_LIST_KEY + date;
    }

    public static String buildBloomUserFollowListKey(String date) {
        return BLOOM_TODAY_USER_FOLLOW_LIST_KEY + date;
    }

    public static String buildBloomUserFansListKey(String date) {
        return BLOOM_TODAY_USER_FANS_LIST_KEY + date;
    }

    public static String buildCountUserKey(Long userId) {
        return COUNT_USER_KEY_PREFIX + userId;
    }

    public static String buildCountNoteKey(Long noteId) {
        return COUNT_NOTE_KEY_PREFIX + noteId;
    }

}

