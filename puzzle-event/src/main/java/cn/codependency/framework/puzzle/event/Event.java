package cn.codependency.framework.puzzle.event;

import java.io.Serializable;
import java.util.Map;

public interface Event extends Serializable {

    String RETRY_TIMES = "retryTimes";

    /**
     * 事件id
     *
     * @return
     */
    String getEventId();

    /**
     * 事件Meta数据
     *
     * @return
     */
    Map<String, Object> getMetadata();


    /**
     * 设置属性
     * @param key
     * @param value
     */
    void setMetadataValue(String key, Object value);
}
