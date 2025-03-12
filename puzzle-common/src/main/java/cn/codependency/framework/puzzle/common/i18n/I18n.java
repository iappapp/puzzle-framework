package cn.codependency.framework.puzzle.common.i18n;

/**
 * @author Liu Chenwei
 * @date 2021-11-18 15:00
 */
public interface I18n {

    /**
     * 字段标识为国际化处理
     * 
     * @param messageKey
     * @return
     */
    static I18nResolvable translate(String messageKey) {
        return new I18nResolvable(messageKey);
    }

    /**
     * 字段标识为国际化处理
     * 
     * @param messageKey
     * @return
     */
    static I18nResolvable translate(String messageKey, String defaultMessage) {
        return new I18nResolvable(messageKey, defaultMessage);
    }

    /**
     * 
     * @param messageKeyable
     * @return
     */
    static I18nResolvable translate(MessageKeyable messageKeyable) {
        if (messageKeyable instanceof I18nResolvable) {
            return (I18nResolvable)messageKeyable;
        }
        return new I18nResolvable(messageKeyable.getMessageKey(), messageKeyable.getDefaultMessage());
    }

}
