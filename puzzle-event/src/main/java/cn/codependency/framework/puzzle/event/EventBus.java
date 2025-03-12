package cn.codependency.framework.puzzle.event;


import java.io.Closeable;

public interface EventBus extends Closeable {


    /**
     * 消息投递
     *
     * @param event
     */
    default void publish(Event event) {
        publish(event, null);
    }


    /**
     * 消息投递
     *
     * @param event
     */
    void publish(Event event, String listener);

    /**
     * @param eventListener
     * @param eventType
     */
    void register(EventListener<?> eventListener, Class<?> eventType);


    void setName(String name);

    String getName();
}
