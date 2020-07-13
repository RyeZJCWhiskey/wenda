package com.nowcoder.wenda.async;

//import sun.java2d.pipe.hw.AccelDeviceEventNotifier;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类的setter方法返回类型全部设置成EventModel，
 * 目的是方便我们可以链式调用用一行代码设置EventModel对象的所有属性,如下：
 * EventModel eventModel = new EventModel();
 * eventModel.setEventType(type).setActorId(actorId).setEntityType(entityType).set...
 */
public class EventModel {
    private EventType type;
    private int actorId;    //事件的发出者，比如谁进行了点赞
    private int entityType; //实体的类型，比如是问题还是评论
    private int entityId;   //一个实体的Id，可以是问题的Id，或者评论的Id等等
    private int entityOwnerId;  //事件的承担着，比如谁发布的问题被点了赞

    private Map<String, String> exts = new HashMap<String, String>(); //扩展字段

    public EventModel(){

    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
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
