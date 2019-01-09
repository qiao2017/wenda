package com.wenda.entity;
/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月9日 下午10:24:17
 * @copyright qiao
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getHeadUrl() {
        return headUrl;
    }
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
