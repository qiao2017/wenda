package com.wenda.service;

import org.springframework.stereotype.Service;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年2月13日 下午2:54:06
 * @copyright qiao
 */
@Service
public class FollowService {
    /**
     * 用户关注某个实体，可以是问题、用户等
     * @param userId 关注用户ID
     * @param entityType 被关注实体类型
     * @param entityId 被关注实体ID
     * @return 关注某个实体是否关注成功
     */
    public boolean follow(int userId, int entityType, int entityId) {
        return false;
    }
    
    /**
     * 用户取消关注某个实体，可以是问题、用户等
     * @param userId 关注用户ID
     * @param entityType 被关注实体类型
     * @param entityId 被关注实体ID
     * @return 取消关注某个实体是否关注成功
     */
    public boolean unFollow(int userId, int entityType, int entityId) {
        return false;
    }
    
    /**
     * 
     * @param entityType
     * @param entityId
     * @return 返回某实体的被关注数量
     */
    public Long followerCount(int entityType, int entityId) {
        return 1L;
    }
}
