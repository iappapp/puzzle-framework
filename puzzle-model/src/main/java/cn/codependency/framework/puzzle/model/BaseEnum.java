package cn.codependency.framework.puzzle.model;


import java.util.Objects;

public interface BaseEnum<K, V> {
    K getKey();

    V getValue();

    static <K, V> K getKey(BaseEnum<K, V> enums) {
        if (Objects.nonNull(enums)) {
            return enums.getKey();
        }
        return null;
    }
}
