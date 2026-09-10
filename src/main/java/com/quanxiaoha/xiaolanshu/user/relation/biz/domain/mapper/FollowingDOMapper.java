package com.quanxiaoha.xiaolanshu.user.relation.biz.domain.mapper;

import com.quanxiaoha.xiaolanshu.user.relation.biz.domain.dataobject.FollowingDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowingDOMapper {

    /**
     * 查询该用户是否有关注关系
     */
    List<FollowingDO> selectByUserId(Long userId);

    // 关注表删除方法
    int deleteByUserIdAndFollowingUserId(@Param("userId") Long userId,
                                         @Param("unfollowUserId") Long unfollowUserId);

    /**
     * 查询记录总数
     *
     * @param userId
     * @return
     */
    long selectCountByuserId(Long userId);

    /**
     * 分页查询
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<FollowingDO> selectPageListByUserId(@Param("userId") Long userId,
                                             @Param("offset") long offset,
                                             @Param("limit") long limit);


    /**
     * 查询关注用户列表
     * @param userId
     * @return
     */
    List<FollowingDO> selectAllByUserId(Long userId);

    int deleteByPrimaryKey(Long id);

    int insert(FollowingDO record);

    int insertSelective(FollowingDO record);

    FollowingDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FollowingDO record);

    int updateByPrimaryKey(FollowingDO record);
}

