package cn.codependency.framework.puzzle.common;

@FunctionalInterface
public interface SupplierWithThrowable<T, E extends Throwable> {

    T get() throws E;
}
