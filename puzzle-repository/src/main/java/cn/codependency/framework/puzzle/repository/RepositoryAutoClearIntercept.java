package cn.codependency.framework.puzzle.repository;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Resource;
import java.util.Objects;


@Slf4j
public class RepositoryAutoClearIntercept implements MethodInterceptor {

    @Resource
    Repositorys repositorys;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            int i = repositorys.increaseReentryTimes();
            if (i == 1) {
                if (Objects.nonNull(invocation.getThis())) {
                    log.debug("[AutoClear Entry]: " + invocation.getThis().getClass().getSimpleName() + "." + invocation.getMethod().getName());
                }
            }
            return invocation.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            final int count = repositorys.releaseReentryTimes();
            if (count <= 0) {
                repositorys.remove();
                if (Objects.nonNull(invocation.getThis())) {
                    log.debug("[AutoClear Leave]: " + invocation.getThis().getClass().getSimpleName() + "." + invocation.getMethod().getName());
                }
            }
        }
    }
}
