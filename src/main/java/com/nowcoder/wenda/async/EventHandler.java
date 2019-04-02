package com.nowcoder.wenda.async;

import java.util.List;

/**
 * EventHandler接口，各种不同类型的EventHandler实体类需要实现这个接口
 */
public interface EventHandler {

    //对感兴趣的EventType进行处理
    void doHandle(EventModel model);

    //让别人知道此Handler关注哪些EventType
    List<EventType> getSupportEventTypes();
}
