package cn.codependency.framework.puzzle.repository.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchExecutorException;
import org.apache.ibatis.executor.ExecutorException;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Slf4j
public class ThrowableProcessor {

    /**
     * 处理异常
     *
     * @param throwable
     * @return
     */
    public static RuntimeException processThrowable(Throwable throwable) {
        Throwable cause = throwable;
        while (Objects.nonNull(cause) && cause instanceof InvocationTargetException) {
            cause = throwable.getCause();
        }

        // 特殊修复ibatis批量执行异常无法序列化的问题
        if (Objects.nonNull(cause) && cause instanceof BatchExecutorException) {
            cause = new ExecutorException(cause.getMessage(), cause.getCause());
        }

        if (Objects.nonNull(cause)) {
            log.error(throwable.getMessage(), throwable);
        }

        return new RuntimeException(cause);
    }

}
