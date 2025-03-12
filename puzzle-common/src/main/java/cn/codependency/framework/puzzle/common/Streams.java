package cn.codependency.framework.puzzle.common;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Streams {

    /**
     * 是否存在
     *
     * @param collection
     * @param predicates
     * @param <T>
     * @return
     */
    static <T> boolean exists(Collection<T> collection, Predicate<? super T>... predicates) {
        if (Objects.isNull(collection)) {
            return false;
        }
        return findStream(collection, predicates).findFirst().isPresent();
    }

    /**
     * 转Map
     *
     * @param collection
     * @param keyMapper
     * @param valueMapper
     * @param <K>
     * @param <V>
     * @param <T>
     * @return
     */
    static <K, V, T> Map<K, V> toMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                     Function<? super T, ? extends V> valueMapper) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * 转Map
     *
     * @param collection
     * @param keyMapper
     * @param valueMapper
     * @param <K>
     * @param <V>
     * @param <T>
     * @return
     */
    static <K, V, T> Map<K, V> toMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                     Function<? super T, ? extends V> valueMapper, BinaryOperator<V> mergeFunction) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    /**
     * 转Map
     *
     * @param collection
     * @param keyMapper
     * @param <K>
     * @param <V>
     * @return
     */
    static <K, V> Map<K, V> toMap(Collection<V> collection, Function<? super V, ? extends K> keyMapper) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return toMap(collection, keyMapper, Function.identity());
    }

    /**
     * 转Map
     *
     * @param collection
     * @param keyMapper
     * @param <K>
     * @param <V>
     * @return
     */
    static <K, V> Map<K, V> toMap(Collection<V> collection, Function<? super V, ? extends K> keyMapper,
                                  BinaryOperator<V> mergeFunction) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return toMap(collection, keyMapper, Function.identity(), mergeFunction);
    }

    /**
     * Group聚合
     *
     * @param collection
     * @param classifier
     * @param <K>
     * @param <V>
     * @return
     */
    static <K, V> Map<K, List<V>> groupBy(Collection<V> collection, Function<? super V, ? extends K> classifier) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return collection.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * 集合格式转换
     *
     * @param collection
     * @param converter
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> List<R> toList(Collection<T> collection, Function<T, R> converter) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return convert(collection, converter, Collectors.toList());
    }

    /**
     * 转Set
     *
     * @param collection
     * @param convert
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> Set<R> toSet(Collection<T> collection, Function<T, R> convert) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return toSet(collection.stream(), convert);
    }

    /**
     * 转Set
     *
     * @param collection
     * @param <T>
     * @return
     */
    static <T> Set<T> toSet(Collection<T> collection) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return convert(collection, Function.identity(), Collectors.toSet());
    }

    /**
     * 转Set
     *
     * @param stream
     * @param converter
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> Set<R> toSet(Stream<T> stream, Function<T, R> converter) {
        if (Objects.isNull(stream)) {
            return null;
        }
        return convert(stream, converter, Collectors.toSet());
    }

    /**
     * 集合格式转换
     *
     * @param stream
     * @param converter
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> List<R> toList(Stream<T> stream, Function<T, R> converter) {
        if (Objects.isNull(stream)) {
            return null;
        }
        return convert(stream, converter, Collectors.toList());
    }

    /**
     * 集合格式转换
     *
     * @param collection
     * @param converter
     * @param collector
     * @param <T>
     * @param <R>
     * @param <C>
     * @return
     */
    static <T, R, C> C convert(Collection<T> collection, Function<T, R> converter,
                               Collector<? super R, ?, C> collector) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return convert(collection.stream(), converter, collector);
    }

    /**
     * 拼接字符串
     *
     * @param collection
     * @param convert
     * @param delimiter
     * @param <T>
     * @return
     */
    static <T> String stringJoin(Collection<T> collection, Function<T, String> convert, String delimiter) {
        return convert(collection, convert, Collectors.joining(delimiter));
    }

    /**
     * 集合格式转换
     *
     * @param stream
     * @param converter
     * @param collector
     * @param <T>
     * @param <R>
     * @param <C>
     * @return
     */
    static <T, R, C> C convert(Stream<T> stream, Function<T, R> converter, Collector<? super R, ?, C> collector) {
        if (Objects.isNull(stream)) {
            return null;
        }
        return stream.map(converter).filter(Check.Function.nonNull()).collect(collector);
    }

    /**
     * 列表过滤工具
     *
     * @param collection
     * @param predicate
     * @param <T>
     * @return
     */
    static <T> List<T> findList(Collection<T> collection, Predicate<? super T>... predicate) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return findStream(collection.stream(), predicate).collect(Collectors.toList());
    }

    /**
     * 查找第一个 查找第一个 查找第一个
     *
     * @param collection
     * @param predicate
     * @param <T>
     * @return
     */
    static <T> T findFirst(Collection<T> collection, Predicate<? super T>... predicate) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return findFirst(collection.stream(), (T)null, predicate);
    }

    static <T> List<T> sorted(Collection<T> collection, Comparator<? super T> comparator) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return collection.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 查找第一个Optional
     *
     * @param collection
     * @param predicates
     * @param <T>
     * @return
     */
    static <T> Optional<T> findFirstOptional(Collection<T> collection, Predicate<? super T>... predicates) {
        if (Objects.isNull(collection)) {
            return Optional.empty();
        }
        return findStream(collection.stream(), predicates).findFirst();
    }

    /**
     * 查找第一个
     *
     * @param stream
     * @param predicate
     * @return
     */
    static <T> T findFirst(Stream<T> stream, Predicate<? super T>... predicate) {
        if (Objects.isNull(stream)) {
            return null;
        }
        return findFirst(stream, (T)null, predicate);
    }

    /**
     * 查找第一个
     *
     * @param stream
     * @param predicates
     * @param orElse
     * @return
     */
    static <T> T findFirst(Stream<T> stream, T orElse, Predicate<? super T>... predicates) {
        if (Objects.isNull(stream)) {
            return null;
        }
        return findStream(stream, predicates).findFirst().orElseGet(() -> orElse);
    }

    /**
     * 查找第一个Optional
     *
     * @param stream
     * @param predicates
     * @param <T>
     * @return
     */
    static <T> Optional<T> findFirstOptional(Stream<T> stream, Predicate<? super T>... predicates) {
        if (Objects.isNull(stream)) {
            return Optional.empty();
        }
        return findStream(stream, predicates).findFirst();
    }

    /**
     * 查找第一个
     *
     * @param stream
     * @param predicates
     * @param orElseGet
     * @return
     */
    static <T> T findFirst(Stream<T> stream, Supplier<T> orElseGet, Predicate<? super T>... predicates) {
        if (Objects.isNull(stream)) {
            return null;
        }
        return findStream(stream, predicates).findFirst().orElseGet(orElseGet);
    }

    /**
     * 列表过滤工具
     *
     * @param stream
     * @param predicates
     * @param <T>
     * @return
     */
    static <T> Stream<T> findStream(Stream<T> stream, Predicate<? super T>... predicates) {
        if (Objects.isNull(stream)) {
            return null;
        }
        for (Predicate<? super T> predicate : predicates) {
            stream = stream.filter(predicate);
        }
        return stream;
    }

    /**
     * 列表过滤工具
     *
     * @param collection
     * @param predicates
     * @param <T>
     * @return
     */
    static <T> Stream<T> findStream(Collection<T> collection, Predicate<? super T>... predicates) {
        if (Objects.isNull(collection)) {
            return null;
        }
        Stream<T> stream = collection.stream();
        for (Predicate<? super T> predicate : predicates) {
            stream = stream.filter(predicate);
        }
        return stream;
    }

    /**
     * reduce集合内对象的某个字段
     *
     * @param collection
     * @param converter
     * @param reduceInitValue
     * @param accumulator
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> R reduce(Collection<T> collection, Function<T, R> converter, R reduceInitValue,
                           BinaryOperator<R> accumulator) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return collection.stream().map(converter).reduce(reduceInitValue, accumulator);
    }

    /**
     * 列表中去除
     *
     * @param data
     * @param except
     * @param <T>
     * @return
     */
    static <T> List<T> excepts(Collection<T> data, Predicate<T> except) {
        if (Objects.isNull(data)) {
            return null;
        }
        return Streams.findList(data, except.negate());
    }
}
