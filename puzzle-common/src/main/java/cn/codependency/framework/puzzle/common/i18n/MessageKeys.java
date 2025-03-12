package cn.codependency.framework.puzzle.common.i18n;

import java.util.Objects;

/**
 * @author Liu Chenwei
 * @date 2021-11-23 11:43
 */
public class MessageKeys {

    /**
     * 获取国际化key
     * 
     * @param obj
     * @return
     */
    public static String getMessageKey(MessageKeyable obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        return obj.getMessageKey();
    }
}
