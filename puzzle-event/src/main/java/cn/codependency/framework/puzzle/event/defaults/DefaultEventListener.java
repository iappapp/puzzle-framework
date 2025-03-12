package cn.codependency.framework.puzzle.event.defaults;

import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.EventListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Objects;

public class DefaultEventListener<T extends Event> implements EventListener<T> {

    ApplicationContext applicationContext;

    String beanName;

    Object target;

    Method method;

    Class<T> eventClazz;

    public DefaultEventListener(ApplicationContext applicationContext, String beanName, Method method, Class<T> eventClazz) {
        this.applicationContext = applicationContext;
        this.beanName = beanName;
        this.method = method;
        this.eventClazz = eventClazz;
    }

    @Override
    public String listenerName() {
        return beanName + "#" + method.getName();
    }

    @Override
    public void onEvent(T event) throws Exception {
        if (Objects.isNull(target) && StringUtils.isNotEmpty(beanName)) {
            synchronized (this) {
                if (Objects.isNull(target)) {
                    target = applicationContext.getBean(beanName);
                }
            }
        }
        method.invoke(target, event);
    }
}
