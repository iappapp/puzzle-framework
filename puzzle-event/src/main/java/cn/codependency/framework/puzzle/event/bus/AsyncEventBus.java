package cn.codependency.framework.puzzle.event.bus;

import cn.codependency.framework.puzzle.event.Event;
import cn.codependency.framework.puzzle.event.EventProcessor;
import cn.codependency.framework.puzzle.common.copy.DeepCopier;
import cn.codependency.framework.puzzle.common.copy.JdkSerializationCopier;
import cn.codependency.framework.puzzle.event.EventListener;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncEventBus extends AbstractEventBus {

    private final ExecutorService executorService;

    private final DeepCopier COPIER = new JdkSerializationCopier();

    public AsyncEventBus(List<EventProcessor> eventProcessors) {
        this(eventProcessors, null);
    }

    public AsyncEventBus(List<EventProcessor> eventProcessors, ExecutorService executorService) {
        super(eventProcessors);
        this.executorService = executorService;
    }

    @Override
    public void publish(Event event, String listenerName) {
        List<EventListener<?>> listeners = getListeners(event.getClass(), listenerName);
        if (CollectionUtils.isNotEmpty(listeners)) {
            // 准备阶段
            this.prepare(listeners, event);
            // 监听
            for (EventListener listener : listeners) {
                Event copy = COPIER.copy(event);
                try {
                    executorService.execute(() -> {
                        Exception ex = null;
                        this.beforeExecute(listener, copy);
                        try {
                            listener.onEvent(copy);
                        } catch (Exception e) {
                            ex = e;
                            this.processError(listener, copy, e);
                        } finally {
                            this.finished(listener, copy, ex);
                        }
                    });
                } catch (Exception e) {
                    this.postError(listener, copy, e);
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
        }
    }
}
