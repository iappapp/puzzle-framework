package cn.codependency.framework.puzzle.event.processor;

import cn.codependency.framework.puzzle.event.*;
import cn.codependency.framework.puzzle.common.exception.ExceptionUtils;
import cn.codependency.framework.puzzle.common.exception.RetryException;
import cn.codependency.framework.puzzle.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class EventRetryProcessor implements EventProcessor {

    /**
     * @param eventCenter
     * @param threadPoolTaskScheduler
     */
    public EventRetryProcessor(EventCenter eventCenter, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.eventCenter = eventCenter;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    EventCenter eventCenter;

    /**
     * 重试调度器
     */
    ThreadPoolTaskScheduler threadPoolTaskScheduler;


    @Override
    public void beforeExecute(EventListener<?> listener, Event event) {
        EventStore eventStore = eventCenter.eventStore();
        Map<String, Object> executeData = eventStore.executeRecord(listener, event.getEventId());
        event.setMetadataValue(Event.RETRY_TIMES, executeData.get(Event.RETRY_TIMES));
    }

    @Override
    public void processError(EventListener<?> listener, Event event, Exception e) {
        RetryException exception = ExceptionUtils.getException(e, RetryException.class);
        if (Objects.nonNull(exception)) {
            EventStore eventStore = eventCenter.eventStore();
            if (Objects.nonNull(eventStore)) {
                eventStore.eventRetry(listener, event, exception);
            }
            log.info(exception.getMessage());
            this.threadPoolTaskScheduler.schedule(() -> eventCenter.publish(event, listener.listenerName()), new Date(exception.getNextRetryTime()));
        }
    }
}
