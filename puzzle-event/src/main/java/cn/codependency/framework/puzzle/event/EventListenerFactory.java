package cn.codependency.framework.puzzle.event;

import java.lang.reflect.Method;

public interface EventListenerFactory {

    /**
     * 事件Listener装配工厂
     *
     * @param eventType
     * @param beanName
     * @param method
     * @return
     */
    <T extends Event> EventListener<T> create(Class<T> eventType, String beanName, Method method);

}
