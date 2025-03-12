package cn.codependency.framework.puzzle.event;


import java.io.Closeable;

/**
 * 消息中心
 */
public interface EventCenter extends Closeable {

    /**
     * 注册
     *
     * @param eventBus
     */
    void register(EventBus eventBus);

    /**
     * 获取事件存储器
     *
     * @return
     */
    EventStore eventStore();

    /**
     * 投递消息
     *
     * @param event 消息
     */
    void publish(Event event);


    /**
     * 投递消息
     *
     * @param event 消息
     */
    void publish(Event event, String listener);
}
