package cn.codependency.framework.puzzle.common;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Check {

    /**
     * 如果 假 则 抛出异常
     *
     * @param verifier
     * @param exceptionSupplier
     *            异常构造者
     * @param <X>
     * @throws X
     */
    static <X extends Exception, Y extends Exception> void ifNotThrow(Verifier<Y> verifier,
                                                                      Supplier<X> exceptionSupplier) throws X, Y {
        ifThrow(!verifier.verify(), exceptionSupplier);
    }

    /**
     * 如果 假 则 抛出异常
     *
     * @param condition
     * @param exceptionSupplier
     *            异常构造者
     * @param <X>
     * @throws X
     */
    static <X extends Exception> void ifNotThrow(boolean condition, Supplier<X> exceptionSupplier) throws X {
        ifThrow(!condition, exceptionSupplier);
    }

    /**
     * 如果 真 则 抛出异常
     *
     * @param verifier
     *            校验
     * @param exceptionSupplier
     *            异常构造者
     * @param <X>
     *            异常
     * @throws X
     */
    static <X extends Exception, Y extends Exception> void ifThrow(Verifier<Y> verifier, Supplier<X> exceptionSupplier)
            throws X, Y {
        ifThrow(verifier.verify(), exceptionSupplier);
    }

    /**
     * 如果 真 则 抛出异常
     *
     * @param condition
     *            条件
     * @param exceptionSupplier
     *            异常构造者
     * @throws X
     */
    static <X extends Exception> void ifThrow(boolean condition, Supplier<X> exceptionSupplier) throws X {
        if (condition) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 如果为空 则 抛出异常 兼容 Optional
     *
     * @param data
     * @param exceptionSupplier
     * @param <X>
     * @throws X
     */
    static <X extends Exception> void ifNullThrow(Object data, Supplier<X> exceptionSupplier) throws X {
        if (data == null || (data instanceof Optional) && (!((Optional)data).isPresent())) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 如果为空 则 抛出异常
     *
     * @param data
     * @param exceptionSupplier
     * @param <X>
     * @throws X
     */
    static <X extends Exception> void ifEmptyThrow(Object data, Supplier<X> exceptionSupplier) throws X {
        if (data == null || (data instanceof String && StringUtils.isEmpty((String)data))
                || (data instanceof Collection && CollectionUtils.isEmpty((Collection<?>)data))
                || (data instanceof Map && MapUtils.isEmpty((Map<?, ?>)data))) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * true 则执行
     *
     * @param target
     * @param predicate
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifThen(T target, Predicate<T> predicate, Consumer<? super T> consumer) {
        if (predicate.test(target)) {
            consumer.accept(target);
            return true;
        }
        return false;
    }

    /**
     * false则执行
     *
     * @param target
     * @param predicate
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifNotThen(T target, Predicate<T> predicate, Consumer<? super T> consumer) {
        if (!predicate.test(target)) {
            consumer.accept(target);
            return true;
        }
        return false;
    }

    /**
     * 非空则执行
     *
     * @param target
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifPresent(T target, Consumer<? super T> consumer) {
        return ifThen(target, Function.nonNull(), consumer);
    }

    /**
     * 空则执行
     *
     * @param target
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifNullThen(T target, Consumer<? super T> consumer) {
        return ifThen(target, Function.isNull(), consumer);
    }

    /**
     * 非空则执行
     *
     * @param target
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifNotNullThen(T target, Consumer<? super T> consumer) {
        return ifThen(target, Function.nonNull(), consumer);
    }

    /**
     * 是否不同
     *
     * @param target
     * @param expect
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifNotEqualsThen(T target, Object expect, Consumer<? super T> consumer) {
        return ifThen(target, t -> !Objects.equals(t, expect), consumer);
    }

    /**
     * 是否相同
     *
     * @param target
     * @param expect
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifEqualsThen(T target, Object expect, Consumer<? super T> consumer) {
        return ifThen(target, t -> Objects.equals(t, expect), consumer);
    }

    /**
     * 如果为空
     *
     * @param target
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifEmptyThen(T target, Consumer<? super T> consumer) {
        return ifThen(target,
                t -> Objects.isNull(t) || (t instanceof String && StringUtils.isEmpty((String)t))
                        || (t instanceof Collection && CollectionUtils.isEmpty((Collection<?>)t))
                        || (t instanceof Map && MapUtils.isEmpty((Map<?, ?>)t)),
                consumer);
    }

    /**
     * 非空白字符串则执行
     *
     * @param target
     * @param consumer
     * @return
     */
    static boolean ifStringNotEmpty(String target, Consumer<String> consumer) {
        return ifThen(target, t -> !StringUtils.isEmpty(t), consumer);
    }

    /**
     * 常用的lambda表达式
     */
    interface Function {

        /**
         * 为空校验
         *
         * @param <T>
         * @return
         */
        static <T> Predicate<T> isNull() {
            return data -> Objects.isNull(data) || (data instanceof Optional) && (!((Optional)data).isPresent());
        }

        /**
         * 非空判断
         *
         * @param <T>
         * @return
         */
        static <T> Predicate<T> nonNull() {
            return data -> !isNull().test(data);
        }

    }

}
