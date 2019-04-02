package com.nowcoder.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个类可以将String与任意类型对象组成键值对
 */
public class ViewObject {
    private Map<String,Object> objs = new HashMap<String, Object>();

    public void set(String key, Object value){
        objs.put(key,value);
    }

    public Object get(String key){
        return objs.get(key);
    }
}
