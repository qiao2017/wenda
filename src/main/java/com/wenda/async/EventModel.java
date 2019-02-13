package com.wenda.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月28日 下午6:51:46
 * @copyright qiao
 */
public class EventModel {
    private EventType eventType;//事件类型
    private int actorId;//事件触发者
    private int entityType;
    private int entityId;
    private int entityOwnerId;
    private Map<String, String> exts = new HashMap<>();
    
    public EventModel() {}
    
    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }
    
    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }
    public String getExt(String key) {
        return exts.get(key);
    }
    public EventType getEventType() {
        return eventType;
    }
    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }
    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;    
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
    
}
