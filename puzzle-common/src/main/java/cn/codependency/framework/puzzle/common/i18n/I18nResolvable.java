package cn.codependency.framework.puzzle.common.i18n;

import org.springframework.context.MessageSourceResolvable;

import java.util.Objects;

/**
 * @author Liu Chenwei
 * @date 2021-11-18 11:38
 */
public class I18nResolvable implements MessageSourceResolvable, MessageKeyable {

    private String[] codes;

    private String defaultMessage;

    public I18nResolvable(String messageKey) {
        if (Objects.isNull(messageKey)) {
            throw new NullPointerException("message code is null");
        }
        this.codes = new String[] {messageKey};
    }

    public I18nResolvable(MessageKeyable messageKeyable) {
        this(messageKeyable.getMessageKey(), messageKeyable.getDefaultMessage());
    }

    public I18nResolvable(String messageKey, String defaultMessage) {
        if (Objects.isNull(messageKey)) {
            throw new NullPointerException("message code is null");
        }
        this.codes = new String[] {messageKey};
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String[] getCodes() {
        return codes;
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String getMessageKey() {
        return this.codes[0];
    }
}
