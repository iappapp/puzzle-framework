package cn.codependency.framework.puzzle.event;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseEvent implements Event {

    public BaseEvent() {
        this.eventId = UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 事件id
     */
    private final String eventId;

    /**
     * metadata
     */
    private Map<String, Object> metadata = new HashMap<>();

    @Override
    public void setMetadataValue(String key, Object value) {
        if (Objects.isNull(metadata)) {
            metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Integer retryTimes() {
        return (Integer) metadata.get(RETRY_TIMES);
    }

    public void setRetryTimes(Integer retryTimes) {
        metadata.put(RETRY_TIMES, retryTimes);
    }
}
