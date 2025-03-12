package cn.codependency.framework.puzzle.event.processor;

import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.annotation.EventHandler;
import cn.codependency.framework.puzzle.event.annotation.EventListener;
import cn.codependency.framework.puzzle.event.EventBus;
import cn.codependency.framework.puzzle.event.EventCenter;
import cn.codependency.framework.puzzle.event.EventListenerFactory;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class EventHandlerPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    EventListenerFactory eventListenerFactory;

    ApplicationContext applicationContext;

    EventCenter eventCenter;

    public EventHandlerPostProcessor(EventCenter eventCenter,  EventListenerFactory eventListenerFactory) {
        this.eventListenerFactory = eventListenerFactory;
        this.eventCenter = eventCenter;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EventBus) {
            ((EventBus) bean).setName(beanName);
            eventCenter.register((EventBus)bean);
        }

        EventHandler eventHandlers = AnnotationUtils.findAnnotation(bean.getClass(), EventHandler.class);
        if (Objects.nonNull(eventHandlers)) {
            ImmutableList<Method> annotatedMethods = EventHandlerRegistry.getAnnotatedMethods(bean.getClass());
            for (Method method : annotatedMethods) {
                EventListener eventListener = AnnotationUtils.findAnnotation(method, EventListener.class);
                if (Objects.nonNull(eventListener)) {
                    // 事件名称和 event bus名称
                    String eventBusName = eventListener.eventBus();
                    if (StringUtils.isEmpty(eventBusName)) {
                        eventBusName = eventHandlers.eventBus();
                    }
                    if (StringUtils.isEmpty(eventBusName)) {
                        eventBusName = "syncEventBus";
                    }
                    EventBus eventBus = (EventBus) this.applicationContext.getBean(eventBusName);
                    eventBus.register(eventListenerFactory.create((Class<? extends Event>) method.getParameterTypes()[0], beanName, method), method.getParameterTypes()[0]);
                }
            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
