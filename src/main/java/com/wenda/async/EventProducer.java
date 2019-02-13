package com.wenda.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wenda.util.JedisAdapter;
import com.wenda.util.RedisKeyUtil;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月28日 下午6:58:06
 * @copyright qiao
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            System.out.println("发送2");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
