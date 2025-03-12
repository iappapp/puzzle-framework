package cn.codependency.framework.puzzle.common.exception;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ExceptionUtils {

    public static boolean isException(Throwable e, Class<? extends Throwable> clazz) {
        if (e instanceof InvocationTargetException) {
            return isException(((InvocationTargetException) e).getTargetException(), clazz);
        }

        if (clazz.isAssignableFrom(e.getClass())) {
            return true;
        }

        if (Objects.nonNull(e.getCause())) {
            return isException(e.getCause(), clazz);
        }
        return false;
    }


    public static <T extends Exception> T getException(Throwable e, Class<T> clazz) {
        if (e instanceof InvocationTargetException) {
            return getException(((InvocationTargetException) e).getTargetException(), clazz);
        }

        if (clazz.isAssignableFrom(e.getClass())) {
            return (T) e;
        }

        if (Objects.nonNull(e.getCause())) {
            return getException(e.getCause(), clazz);
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(ExceptionUtils.isException(new RuntimeException(new RetryException("xxx", 1000)), RetryException.class));
    }
}
