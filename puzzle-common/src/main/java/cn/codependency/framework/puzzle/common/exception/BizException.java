package cn.codependency.framework.puzzle.common.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private String code;

    public BizException(String message) {
        super(message);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }
}
