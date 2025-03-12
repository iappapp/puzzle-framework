package cn.codependency.framework.puzzle.event;


import cn.codependency.framework.puzzle.common.exception.RetryException;

import java.util.List;
import java.util.Map;

/**
 * 消息存储
 */
public interface EventStore {

    /**
     * 添加事件
     *
     * @param event
     */
    default void put(Event event) {

    }


    /**
     * 获取消息
     *
     * @param id
     * @param <T>
     * @return
     */
    default <T extends Event> T get(String id) {
        return null;
    }


    default Map<String, Object> executeRecord(EventListener<?> listener, String id) {
        return null;
    }

    /**
     * 消息待投递记录
     *
     * @param listeners
     * @param event
     */
    default void eventPosted(List<EventListener<?>> listeners, Event event) {

    }

    /**
     * 事件消费完成
     *
     * @param listener
     * @param event
     */
    default void eventCompleted(EventListener<?> listener, Event event) {

    }


    /**
     * 事件重试
     *
     * @param listener
     * @param event
     */
    default void eventRetry(EventListener<?> listener, Event event, RetryException e) {

    }


    /**
     * 事件消费异常
     *
     * @param listener
     * @param event
     */
    default void eventProcessError(EventListener<?> listener, Event event, Exception e) {

    }

    /**
     * 消息未投递成功
     *
     * @param listener
     * @param event
     */
    default void eventUnPosted(String listener, Event event) {

    }
}
