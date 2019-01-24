package com.wenda.entity;

import java.util.Date;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午6:36:59
 * @copyright qiao
 */
public class Message {
    public static final int READ = 0;
    public static final int UNREAD = 1;
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createDate;
    private int hasRead;
    private String conversationId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getFromId() {
        return fromId;
    }
    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    public int getToId() {
        return toId;
    }
    public void setToId(int toId) {
        this.toId = toId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public int getHasRead() {
        return hasRead;
    }
    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }
    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        }else {
            return String.format("%d_%d", toId, fromId);
        }
    }
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
