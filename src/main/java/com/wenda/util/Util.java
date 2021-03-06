package com.wenda.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午9:14:37
 * @copyright qiao
 */
public class Util {
    public static final int ANONYMOUS = 0;
    public static final int SYSTEM_USERID = 1;
    
    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
              // 生成一个MD5加密计算摘要  
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
    
    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }
    
    public static String getJSONString(int code, Map<String, Object> data) {
        JSONObject json = new JSONObject();
        json.put("code", code); 
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            json.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
        }
        return json.toJSONString();
    }
}
