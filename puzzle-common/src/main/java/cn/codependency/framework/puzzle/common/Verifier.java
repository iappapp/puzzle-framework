package cn.codependency.framework.puzzle.common;

@FunctionalInterface
public interface Verifier<X extends Exception> {

    /**
     * 校验
     * @return
     * @throws X
     */
    boolean verify() throws X;
}
