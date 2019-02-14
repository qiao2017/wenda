package com.wenda.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenda.util.JedisAdapter;
import com.wenda.util.RedisKeyUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年2月13日 下午2:54:06
 * @copyright qiao
 */
@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;
    /**
     * 用户关注某个实体，可以是问题、用户等
     * @param userId 关注用户ID
     * @param entityType 被关注实体类型
     * @param entityId 被关注实体ID
     * @return 关注某个实体是否关注成功
     */
    public boolean follow(int userId, int entityType, int entityId) {
        Jedis jedis = jedisAdapter.getJedis();
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        Date date = new Date();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (long)ret.get(0) > 0 && (long)ret.get(1) > 0;
    }
    
    /**
     * 用户取消关注某个实体，可以是问题、用户等
     * @param userId 关注用户ID
     * @param entityType 被关注实体类型
     * @param entityId 被关注实体ID
     * @return 取消关注某个实体是否关注成功
     */
    public boolean unFollow(int userId, int entityType, int entityId) {
        Jedis jedis = jedisAdapter.getJedis();
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zrem(followerKey, String.valueOf(userId));
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (long)ret.get(0) > 0 && (long)ret.get(1) > 0;
    }

    
    /**
     * 根据实体，获得该实体的所有关注者
     * @param entityType
     * @param entityId
     * @param count
     * @return
     */
    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));
    }
    
    /**
     * 根据实体，获得该实体的所有关注者
     */
    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset, offset+count));
    }

    /**
     * 获得用户所关注的某类实体的全部
     * @param userId
     * @param entityType
     * @param count
     * @return
     */
    public List<Integer> getFollowees(int userId, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }
    
    /**
     * 获得用户所关注的某类实体的全部
     * @param userId
     * @param entityType
     * @param offset
     * @param count
     * @return
     */
    public List<Integer> getFollowees(int userId, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset+count));
    }
    /**
     * 获得该实体的被关注数量
     * @param entityType
     * @param entityId
     * @return
     */
    public long getFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    /**
     * 
     * @param userId
     * @param entityType
     * @return
     */
    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        return jedisAdapter.zcard(followeeKey);
    }

    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    /**
     *  判断用户是否关注了某个实体
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }
}
