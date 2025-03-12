package cn.codependency.framework.puzzle.common;

import cn.codependency.framework.puzzle.common.error.ErrorCode;
import cn.codependency.framework.puzzle.common.exception.BizException;
import cn.codependency.framework.puzzle.common.exception.RetryException;
import cn.codependency.framework.puzzle.common.exception.SystemError;

public interface Errors {

    /**
     * 异常构建
     *
     * @param message
     * @return
     */
    static BizException message(String message) {
        return new BizException(message);
    }

    static BizException message(ErrorCode errorCode) {
        return new BizException(String.valueOf(errorCode.getCode()), errorCode.getMessage());
    }

    static BizException message(ErrorCode errorCode, Object... args) {
        return new BizException(String.valueOf(errorCode.getCode()), errorCode.getMessage(args));
    }

    /**
     * 带code的异常构建
     *
     * @param code
     * @param message
     * @return
     */
    static BizException message(String code, String message) {
        return new BizException(code, message);
    }


    /**
     * 重试异常
     *
     * @param message
     * @param afterTimes
     * @return
     */
    static RetryException retry(String message, long afterTimes) {
        return new RetryException(message, System.currentTimeMillis() + afterTimes);
    }


    /**
     * 重试异常
     *
     * @param message
     * @param afterTimes
     * @return
     */
    static RetryException retry(String code, String message, long afterTimes) {
        return new RetryException(code, message, System.currentTimeMillis() + afterTimes);
    }


    /**
     * 系统异常
     *
     * @param message
     * @return
     */
    static BizException system(String message) {
        return new SystemError(message);
    }
}
