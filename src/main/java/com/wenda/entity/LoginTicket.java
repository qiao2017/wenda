package com.wenda.entity;

import java.util.Date;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月9日 下午10:44:43
 * @copyright qiao
 */
public class LoginTicket {
    public static final String VALID = "0";
    public static final String IN_VALID = "1";
    private int id;
    private int userId;
    private Date expired;
    private String status;// 0有效，1无效
    private String ticket;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Date getExpired() {
        return expired;
    }
    public void setExpired(Date expired) {
        this.expired = expired;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
