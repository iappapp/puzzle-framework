package cn.codependency.framework.puzzle.event;

public interface EventListener<T extends Event> {

    String listenerName();

    void onEvent(T event) throws Exception;
}
