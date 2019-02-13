package com.wenda.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wenda.util.JedisAdapter;
import com.wenda.util.RedisKeyUtil;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年2月11日 下午7:20:53
 * @copyright qiao
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;
    
    @Autowired
    JedisAdapter jedisAdapter;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        
        if(beans != null) {
            for(Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                logger.info("EventHandler的实现类" + entry.getKey() + "已加入配置");
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for(EventType type : eventTypes) {
                    if(!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        
        //开启线程，异步执行任务
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                String key = RedisKeyUtil.getEventQueueKey();
                while(true) {
                    List<String> events = jedisAdapter.brpop(0, key);
                    
                    for(String message : events) {
                        if(message.equals(key)) {
                            continue;
                        }
                        
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if(!config.containsKey(eventModel.getEventType())) {
                            logger.error("非法事件！");
                            continue;
                        }
                        
                        for(EventHandler handler : config.get(eventModel.getEventType())) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.applicationContext = arg0;
    }
    
}
