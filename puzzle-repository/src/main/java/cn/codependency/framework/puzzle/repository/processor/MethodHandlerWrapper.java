package cn.codependency.framework.puzzle.repository.processor;

import java.lang.reflect.Method;

public class MethodHandlerWrapper implements MethodHandler {

    private Object target;

    private Method method;

    public MethodHandlerWrapper(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return method.invoke(target, args);
    }

    @Override
    public String getInvokeMethodName() {
        return method.toString();
    }
}
