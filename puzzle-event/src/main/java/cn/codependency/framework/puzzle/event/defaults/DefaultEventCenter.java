package cn.codependency.framework.puzzle.event.defaults;


import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.EventBus;
import cn.codependency.framework.puzzle.event.EventCenter;
import cn.codependency.framework.puzzle.event.EventStore;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.SmartLifecycle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 默认事件中心实现
 */
@Slf4j
public class DefaultEventCenter implements EventCenter, SmartLifecycle {

    /**
     * 事件总线
     */
    protected List<EventBus> eventBuses;

    protected EventStore eventStore;

    @Setter
    protected Integer phase;

    protected AtomicBoolean stop = new AtomicBoolean(false);

    public DefaultEventCenter() {
        this.eventBuses = new CopyOnWriteArrayList<>();
    }

    public DefaultEventCenter(EventStore eventStore) {
        this();
        this.eventStore = eventStore;
    }

    @Override
    public void register(EventBus eventBus) {
        eventBuses.add(eventBus);
    }

    @Override
    public EventStore eventStore() {
        return eventStore;
    }

    /**
     * 消息投递
     *
     * @param event 消息
     */
    @Override
    public void publish(Event event) {
        publish(event, null);
    }

    @Override
    public void publish(Event event, String listener) {
        if (Objects.nonNull(eventStore)) {
            eventStore.put(event);
            if (stop.get()) {
                eventStore.eventUnPosted(listener, event);
            }
        }

        if (!stop.get()) {
            for (EventBus eventBus : eventBuses) {
                eventBus.publish(event, listener);
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (stop.compareAndSet(false, true)) {
            log.info(String.format("[%s]消息中心关闭中...", this.getClass().getSimpleName()));

            if (CollectionUtils.isNotEmpty(eventBuses)) {
                for (EventBus eventBus : eventBuses) {
                    eventBus.close();
                }
            }
        }
    }

    @Override
    public void start() {
    }

    @SneakyThrows
    @Override
    public void stop() {
        close();
    }

    @Override
    public int getPhase() {
        return Objects.isNull(phase) ? Integer.MAX_VALUE : phase;
    }

    @Override
    public boolean isRunning() {
        return !stop.get();
    }
}
