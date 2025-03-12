package cn.codependency.framework.puzzle.event.defaults;

import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.EventListener;
import cn.codependency.framework.puzzle.event.EventListenerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

public class DefaultEventListenerFactory implements EventListenerFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T extends Event> EventListener<T> create(Class<T> eventType, String beanName, Method method) {
        return new DefaultEventListener<>(applicationContext, beanName, method, eventType);
    }
}
