package com.wenda.async;
/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月28日 下午6:49:02
 * @copyright qiao
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);
    
    private int value;
    EventType(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
