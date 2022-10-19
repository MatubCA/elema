package com.elema.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果类
 */
@Data
public class Results<T> {
    private Integer code; // 响应码
    private String msg; // 错误消息
    private T data; // 数据
    private Map<String, Object> map = new HashMap<>(); // 动态数据

    public static <T> Results<T> success(T object){
        Results<T> results = new Results<>();
        results.data = object;
        results.code = 1;
        return results;
    }

    public static <T> Results<T> error(String msg) {
        Results<T> results = new Results<>();
        results.code = 0;
        results.msg = msg;
        return results;
    }

    public Results<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
