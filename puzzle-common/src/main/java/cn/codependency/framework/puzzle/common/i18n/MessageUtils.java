package cn.codependency.framework.puzzle.common.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Objects;

/**
 * @author Liu Chenwei
 * @date 2021-11-15 15:07
 */
public class MessageUtils {

    private static volatile MessageSource messageSource;

    /**
     * 中文翻译常量前缀
     */
    public static volatile String CONSTANT_PREFIX = "i18.message.constant.";

    /**
     * 初始化消息源
     * 
     * @param messageSource
     */
    public static synchronized void init(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    /**
     * 获取国际化翻译，未匹配不返回中文，不建议使用
     */
    @Deprecated
    public static String get(String messageKey) {
        return get(I18n.translate(messageKey));
    }

    /**
     * 获取国际化翻译
     */
    public static String get(String messageKey, String defaultMessage) {
        return get(I18n.translate(messageKey, defaultMessage));
    }

    /**
     * 获取国际化翻译，未匹配不返回中文，不建议使用
     */
    @Deprecated
    public static String get(String messageKey, Object[] objects) {
        return get(I18n.translate(messageKey), objects);
    }

    /**
     * 获取国际化翻译
     */
    public static String get(MessageKeyable messageKeyable) {
        return get(messageKeyable, null);
    }

    /**
     * 获取国际化翻译
     */
    public static String get(MessageKeyable messageKeyable, Object... objects) {
        try {
            if (Objects.isNull(messageSource)) {
                return messageKeyable.getDefaultMessage();
            }
            Object[] params = objects;
            if (Objects.nonNull(objects)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (Objects.nonNull(param) && param instanceof MessageKeyable) {
                        if (param instanceof I18nResolvable) {
                            continue;
                        }
                        params[i] = I18n.translate((MessageKeyable)param);
                    }
                }
            }
            return messageSource.getMessage(messageKeyable.getMessageKey(), params, messageKeyable.getDefaultMessage(),
                LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return messageKeyable.getDefaultMessage();
        }
    }

    /**
     * 翻译中文内容，会自动根据前缀生成对应的messageKey，未匹配到则返回中文
     * 
     * @param chineseContent
     *            中文内容
     * @param chineseParams
     *            中文占位信息
     * @return
     */
    public static String translate(String chineseContent, String... chineseParams) {
        Object[] objParams = null;
        if (Objects.nonNull(chineseParams)) {
            objParams = new Object[chineseParams.length];
            for (int i = 0; i < objParams.length; i++) {
                if (Objects.isNull(chineseParams[i])) {
                    objParams[i] = null;
                } else {
                    objParams[i] = I18n.translate(CONSTANT_PREFIX + chineseParams[i], chineseParams[i]);
                }
            }
        }
        return get(I18n.translate(CONSTANT_PREFIX + chineseContent, chineseContent), objParams);
    }
}
