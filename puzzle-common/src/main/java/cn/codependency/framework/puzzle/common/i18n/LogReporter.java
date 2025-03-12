package cn.codependency.framework.puzzle.common.i18n;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Liu Chenwei
 * @date 2022-04-25 20:48
 */
@Slf4j
public class LogReporter implements Reporter {

    Set<String> reporters = new HashSet<>();

    @Override
    public void unmatchedReport(String messageKey, String defaultMessage, Locale locale) {
        if (locale == Locale.CHINA) {
            return;
        }
        if (reporters.add(messageKey + defaultMessage + locale)) {
            log.info(String.format("新增翻译项: %s， key: %s， locale: %s", defaultMessage, messageKey, locale));
        }
    }
}
