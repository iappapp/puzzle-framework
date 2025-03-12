package cn.codependency.framework.puzzle.event.bus;

import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.EventBus;
import cn.codependency.framework.puzzle.event.EventProcessor;
import cn.codependency.framework.puzzle.common.Streams;
import cn.codependency.framework.puzzle.event.EventListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class AbstractEventBus implements EventBus {

    @Getter
    @Setter
    private String name;

    private Map<Class<?>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

    private List<EventProcessor> eventProcessors;


    public AbstractEventBus() {
    }

    public AbstractEventBus(List<EventProcessor> eventProcessors) {
        this.eventProcessors = eventProcessors;
    }

    /**
     * 获取Listener
     *
     * @param clazz
     * @return
     */
    protected List<EventListener<?>> getListeners(Class<?> clazz) {
        return listeners.getOrDefault(clazz, new ArrayList<>());
    }

    /**
     * 获取Listener
     *
     * @param clazz
     * @return
     */
    protected List<EventListener<?>> getListeners(Class<?> clazz, String listener) {
        List<EventListener<?>> eventListeners = listeners.getOrDefault(clazz, new ArrayList<>());
        if (Objects.isNull(listener)) {
            return eventListeners;
        }
        if (CollectionUtils.isEmpty(eventListeners)) {
            return eventListeners;
        }
        return Streams.findList(eventListeners, e -> Objects.equals(e.listenerName(), listener));

    }


    /**
     * 注册
     *
     * @param eventListener
     * @param eventType
     */
    @Override
    public synchronized void register(EventListener<?> eventListener, Class<?> eventType) {
        List<EventListener<?>> eventListeners = this.listeners.get(eventType);
        if (Objects.isNull(eventListeners)) {
            eventListeners = new ArrayList<>();
            this.listeners.put(eventType, eventListeners);
        }
        eventListeners.add(eventListener);
    }

    /**
     * 消息发送前准备
     *
     * @param listeners
     * @param event
     */
    protected void prepare(List<EventListener<?>> listeners, Event event) {
        if (CollectionUtils.isNotEmpty(eventProcessors)) {
            for (EventProcessor eventProcessor : eventProcessors) {
                eventProcessor.prepare(listeners, event);
            }
        }
    }

    protected void beforeExecute(EventListener<?> listener, Event event) {
        if (CollectionUtils.isNotEmpty(eventProcessors)) {
            for (EventProcessor eventProcessor : eventProcessors) {
                eventProcessor.beforeExecute(listener, event);
            }
        }
    }

    protected void finished(EventListener<?> listener, Event event, Exception e) {
        if (CollectionUtils.isNotEmpty(eventProcessors)) {
            for (EventProcessor eventProcessor : eventProcessors) {
                eventProcessor.finished(listener, event, e);
            }
        }
    }

    /**
     * 处理异常
     *
     * @param listener
     * @param event
     * @param e
     */
    protected void processError(EventListener<?> listener, Event event, Exception e) {
        if (CollectionUtils.isNotEmpty(eventProcessors)) {
            for (EventProcessor eventProcessor : eventProcessors) {
                eventProcessor.processError(listener, event, e);
            }
        }
    }

    /**
     * 处理异常
     *
     * @param listener
     * @param event
     * @param e
     */
    protected void postError(EventListener<?> listener, Event event, Exception e) {
        if (CollectionUtils.isNotEmpty(eventProcessors)) {
            for (EventProcessor eventProcessor : eventProcessors) {
                eventProcessor.postError(listener, event, e);
            }
        }
    }

    @Override
    public void close() throws IOException {

    }


}
