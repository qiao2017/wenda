package com.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenda.util.JedisAdapter;
import com.wenda.util.RedisKeyUtil;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月27日 下午6:45:27
 * @copyright qiao
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;
    

    
    public long like(int userId, int entityType, int entityId) {
        //添加进喜欢集合
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            //取消赞同
            jedisAdapter.srem(likeKey, String.valueOf(userId));
        }else {
            //添加赞同
            jedisAdapter.sadd(likeKey, String.valueOf(userId));
        }
        
        //从不喜欢集合中移除
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        
        //返回喜欢人数
        return jedisAdapter.scard(likeKey);
    }
    
    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(disLikeKey, String.valueOf(userId))) {
            //取消踩
            jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        }else {
            //踩
            jedisAdapter.sadd(disLikeKey, String.valueOf(userId));
        }
        
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        
        return jedisAdapter.scard(likeKey);
    }
    
    public long getLikedCount(int entityType, int entityId) {
        String key = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(key);
    }
    
    public int getLikedStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(disLikeKey, String.valueOf(userId))) {
            return -1;
        }else {
            return 0;
        }
    }
}
