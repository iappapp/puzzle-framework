package cn.codependency.framework.puzzle.event;

import java.util.List;

public interface EventProcessor {

    default void prepare(List<EventListener<?>> listeners, Event event) {

    }

    default void beforeExecute(EventListener<?> listener, Event event) {

    }


    default void finished(EventListener<?> listener, Event event, Exception e) {

    }

    default void processError(EventListener<?> listener, Event event, Exception e) {

    }

    default void postError(EventListener<?> listener, Event event, Exception e) {

    }
}
