package cn.codependency.framework.puzzle.common;

import java.util.Objects;

public class Ops {

    public static <T> boolean in(T value, T... inValue) {
        if (inValue == null) {
            return false;
        }

        for (T val : inValue) {
            if (Objects.equals(value, val)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static boolean isTrue(Boolean obj) {
        return Objects.nonNull(obj) && obj;
    }

    public static boolean isFalse(Boolean obj) {
        return !isTrue(obj);
    }

}
