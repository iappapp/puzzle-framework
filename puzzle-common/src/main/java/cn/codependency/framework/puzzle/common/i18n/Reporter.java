package cn.codependency.framework.puzzle.common.i18n;

import java.util.Locale;

/**
 * @author Liu Chenwei
 * @date 2022-04-25 20:40
 */
public interface Reporter {

    /**
     * 未匹配消息上报
     * 
     * @param messageKey
     * @param defaultMessage
     * @param locale
     */
    default void unmatchedReport(String messageKey, String defaultMessage, Locale locale) {
        return;
    }

}
