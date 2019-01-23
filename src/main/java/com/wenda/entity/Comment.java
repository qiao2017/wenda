package com.wenda.entity;

import java.util.Date;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午2:53:16
 * @copyright qiao
 */
public class Comment {
    public static final int VALID = 0;
    public static final int INVALID = 1;
    private int id;
    private String content;
    private int entityId;
    private int entityType;
    private Date createDate;
    private int userId;
    private int status;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getEntityId() {
        return entityId;
    }
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
    public int getEntityType() {
        return entityType;
    }
    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
