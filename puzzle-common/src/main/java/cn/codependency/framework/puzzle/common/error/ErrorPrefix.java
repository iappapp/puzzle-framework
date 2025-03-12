package cn.codependency.framework.puzzle.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Liu Chenwei
 * @date 2021-11-18 10:17
 */
@Getter
@AllArgsConstructor
public enum ErrorPrefix {

    /**
     * 系统异常
     */
    SYSTEM_ERROR(0),
    /**
     * 授权异常
     */
    AUTH_ERROR(1),
    /**
     * 参数异常
     */
    PARAM_ERROR(2),

    /**
     * 业务异常
     */
    BIZ_ERROR(3),

    ;

    private int prefix;

}
