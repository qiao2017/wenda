package com.wenda.async;

import java.util.List;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年2月11日 下午7:18:36
 * @copyright qiao
 */
public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();
}
