package cn.codependency.framework.puzzle.event.configuration;

import cn.codependency.framework.puzzle.event.EventCenter;
import cn.codependency.framework.puzzle.event.EventListenerFactory;
import cn.codependency.framework.puzzle.event.EventStore;
import cn.codependency.framework.puzzle.event.bus.SyncEventBus;
import cn.codependency.framework.puzzle.event.defaults.DefaultEventCenter;
import cn.codependency.framework.puzzle.event.defaults.DefaultEventListenerFactory;
import cn.codependency.framework.puzzle.event.defaults.NoneEventStore;
import cn.codependency.framework.puzzle.event.processor.EventHandlerPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class EventAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(EventStore.class)
    public EventStore eventStore() {
        return new NoneEventStore();
    }

    @Bean
    @ConditionalOnMissingBean(EventCenter.class)
    public EventCenter eventCenter(EventStore eventStore) {
        return new DefaultEventCenter(eventStore);
    }

    @Bean
    @ConditionalOnMissingBean(EventListenerFactory.class)
    public EventListenerFactory eventListenerFactory() {
        return new DefaultEventListenerFactory();
    }

    @Bean
    public EventHandlerPostProcessor eventHandlersPostProcessor(EventCenter eventCenter, EventListenerFactory eventListenerFactory) {
        return new EventHandlerPostProcessor(eventCenter, eventListenerFactory);
    }

    @Bean
    public SyncEventBus syncEventBus() {
        return new SyncEventBus();
    }

}
