package cn.codependency.framework.puzzle.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Liu Chenwei
 * @date 2021-12-02 15:33
 */
@Getter
@AllArgsConstructor
public enum ResponseCode implements ErrorCode {

    SUCCESS(100, "result.code.success", "成功")
    ;

    private Integer errorCode;

    private String messageKey;

    private String desc;

    ;

    @Override
    public Integer getCodePrefix() {
        return 0;
    }
}
