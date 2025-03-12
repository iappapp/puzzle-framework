package cn.codependency.framework.puzzle.repository.processor;

public interface MethodHandler {

    /**
     * 方法执行器
     *
     * @param args
     * @return
     * @throws Throwable
     */
    Object invoke(Object[] args) throws Throwable;

    /**
     * 获取调用方法名
     *
     * @return
     */
    String getInvokeMethodName();

}
