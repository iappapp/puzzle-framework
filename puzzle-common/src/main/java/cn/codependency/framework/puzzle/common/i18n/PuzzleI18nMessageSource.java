package cn.codependency.framework.puzzle.common.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author Liu Chenwei
 * @date 2021-11-18 13:59
 */
public class PuzzleI18nMessageSource extends MessageSourceSupport implements MessageSource {

    /**
     * 代理消息源
     */
    private List<AbstractMessageSource> delegatingMessageSources;

    /**
     * 上报
     */
    private Reporter reporter;

    public PuzzleI18nMessageSource() {}

    public PuzzleI18nMessageSource(Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     * 增加国际化消息源
     *
     * @param messageSource
     */
    public synchronized void addI18nMessageSource(AbstractMessageSource messageSource) {
        if (Objects.isNull(delegatingMessageSources)) {
            this.delegatingMessageSources = new LinkedList<>();
        }
        delegatingMessageSources.add(messageSource);
    }

    /**
     * 调用代理消息源的code解析方法
     * 
     * @param s
     * @param locale
     * @return
     */
    protected MessageFormat resolveCode(String s, Locale locale) {
        for (AbstractMessageSource delegatingMessageSource : delegatingMessageSources) {

            Method resolveCode = ReflectionUtils.findMethod(delegatingMessageSource.getClass(), "resolveCode",
                String.class, Locale.class);
            resolveCode.setAccessible(true);
            try {
                Object ret = resolveCode.invoke(delegatingMessageSource, s, locale);
                if (Objects.nonNull(ret) && ret instanceof MessageFormat) {
                    return (MessageFormat)ret;
                }
            } catch (Exception ignore) {
                logger.error("i18n消息解析失败", ignore);
            }
        }
        return null;
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String msg = getMessageInternal(code, args, locale);
        if (msg != null) {
            return msg;
        }
        if (defaultMessage == null) {
            String fallback = getDefaultMessage(code);
            if (fallback != null) {
                return fallback;
            }
        }
        if (!StringUtils.isEmpty(defaultMessage)) {
            reporter.unmatchedReport(code, defaultMessage, locale);
        }
        return renderDefaultMessage(defaultMessage, args, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) {
        return getMessage(code, args, null, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        String[] codes = resolvable.getCodes();
        if (codes == null) {
            codes = new String[0];
        }
        for (String code : codes) {
            String msg = getMessageInternal(code, resolvable.getArguments(), locale);
            if (msg != null) {
                return msg;
            }
        }
        String defaultMessage = resolvable.getDefaultMessage();
        if (defaultMessage != null) {
            reporter.unmatchedReport(codes[0], defaultMessage, locale);
            return renderDefaultMessage(defaultMessage, resolvable.getArguments(), locale);
        }
        if (codes.length > 0) {
            String fallback = getDefaultMessage(codes[0]);
            if (fallback != null) {
                return fallback;
            }
        }
        return null;
    }

    /**
     * Resolve the given code and arguments as message in the given Locale, returning {@code null} if not found. Does
     * <i>not</i> fall back to the code as default message. Invoked by {@code getMessage} methods.
     * 
     * @param code
     *            the code to lookup up, such as 'calculator.noRateSet'
     * @param args
     *            array of arguments that will be filled in for params within the message
     * @param locale
     *            the Locale in which to do the lookup
     * @return the resolved message, or {@code null} if not found
     * @see #getMessage(String, Object[], String, Locale)
     * @see #getMessage(String, Object[], Locale)
     * @see #getMessage(MessageSourceResolvable, Locale)
     * 
     */
    protected String getMessageInternal(String code, Object[] args, Locale locale) {
        if (code == null) {
            return null;
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        Object[] argsToUse = args;

        if (!isAlwaysUseMessageFormat() && ObjectUtils.isEmpty(args)) {
            // Optimized resolution: no arguments to apply,
            // therefore no MessageFormat needs to be involved.
            // Note that the default implementation still uses MessageFormat;
            // this can be overridden in specific subclasses.
            String message = resolveCodeWithoutArguments(code, locale);
            if (message != null) {
                return message;
            }
        }

        else {
            // Resolve arguments eagerly, for the case where the message
            // is defined in a parent MessageSource but resolvable arguments
            // are defined in the child MessageSource.
            argsToUse = resolveArguments(args, locale);

            MessageFormat messageFormat = resolveCode(code, locale);
            if (messageFormat != null) {
                synchronized (messageFormat) {
                    return messageFormat.format(argsToUse);
                }
            }
        }

        return null;
    }

    /**
     * Return a fallback default message for the given code, if any.
     * <p>
     * Default is to return the code itself if "useCodeAsDefaultMessage" is activated, or return no fallback else. In
     * case of no fallback, the caller will usually receive a NoSuchMessageException from {@code getMessage}.
     * 
     * @param code
     *            the message code that we couldn't resolve and that we didn't receive an explicit default message for
     * @return the default message to use, or {@code null} if none
     */
    protected String getDefaultMessage(String code) {
        return code;
    }

    /**
     * Searches through the given array of objects, finds any MessageSourceResolvable objects and resolves them.
     * <p>
     * Allows for messages to have MessageSourceResolvables as arguments.
     * 
     * @param args
     *            array of arguments for a message
     * @param locale
     *            the locale to resolve through
     * @return an array of arguments with any MessageSourceResolvables resolved
     */
    @Override
    protected Object[] resolveArguments(Object[] args, Locale locale) {
        if (args == null) {
            return new Object[0];
        }
        List<Object> resolvedArgs = new ArrayList<Object>(args.length);
        for (Object arg : args) {
            if (arg instanceof MessageSourceResolvable) {
                resolvedArgs.add(getMessage((MessageSourceResolvable)arg, locale));
            } else {
                resolvedArgs.add(arg);
            }
        }
        return resolvedArgs.toArray(new Object[resolvedArgs.size()]);
    }

    /**
     * Subclasses can override this method to resolve a message without arguments in an optimized fashion, i.e. to
     * resolve without involving a MessageFormat.
     * <p>
     * The default implementation <i>does</i> use MessageFormat, through delegating to the {@link #resolveCode} method.
     * Subclasses are encouraged to replace this with optimized resolution.
     * <p>
     * Unfortunately, {@code java.text.MessageFormat} is not implemented in an efficient fashion. In particular, it does
     * not detect that a message pattern doesn't contain argument placeholders in the first place. Therefore, it is
     * advisable to circumvent MessageFormat for messages without arguments.
     * 
     * @param code
     *            the code of the message to resolve
     * @param locale
     *            the Locale to resolve the code for (subclasses are encouraged to support internationalization)
     * @return the message String, or {@code null} if not found
     * @see #resolveCode
     * @see MessageFormat
     */
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        // 尝试直接通过code获取message
        for (AbstractMessageSource delegatingMessageSource : delegatingMessageSources) {
            if (delegatingMessageSource instanceof ResourceBundleMessageSource) {
                String message = delegatingMessageSource.getMessage(code, null, locale);
                if (!Objects.equals(message, code)) {
                    return message;
                }
            } else {
                Method resolveCode = ReflectionUtils.findMethod(delegatingMessageSource.getClass(), "resolveCode",
                        String.class, Locale.class);
                resolveCode.setAccessible(true);
                try {
                    Object ret = resolveCode.invoke(delegatingMessageSource, code, locale);
                    if (Objects.nonNull(ret) && ret instanceof MessageFormat) {
                        return ((MessageFormat) ret).format(new Object[0]);
                    }
                } catch (Exception ignore) {
                    logger.error("i18n消息解析失败", ignore);
                }
            }
        }
        return null;
    }
}
