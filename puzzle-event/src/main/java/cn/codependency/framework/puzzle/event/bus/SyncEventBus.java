package cn.codependency.framework.puzzle.event.bus;

import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.EventListener;

import java.util.List;

public class SyncEventBus extends AbstractEventBus {


    @Override
    public void publish(Event event, String listenerName) {
        List<EventListener<?>> listeners = getListeners(event.getClass(), listenerName);
        for (EventListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                processError(listener, event, e);
            }
        }
    }
}
