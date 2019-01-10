package com.wenda.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午8:59:18
 * @copyright qiao
 */
public class ViewObject {
    private Map<String, Object> map = new HashMap<>();
    public void set(String key, Object value) {
        map.put(key, value);
    }
    public Object get(String key) {
        return map.get(key);
    }
}
