package cn.codependency.framework.puzzle.common.exception;

public class SystemError extends BizException{

    public SystemError(String message) {
        super(message);
    }

    public SystemError(String code, String message) {
        super(code, message);
    }
}
