package cn.codependency.framework.puzzle.common.exception;

import lombok.Getter;

@Getter
public class RetryException extends BizException {

    private final long nextRetryTime;

    public RetryException(String message, long nextRetryTime) {
        super(message);
        this.nextRetryTime = nextRetryTime;
    }

    public RetryException(String code, String message, long nextRetryTime) {
        super(code, message);
        this.nextRetryTime = nextRetryTime;
    }
}
