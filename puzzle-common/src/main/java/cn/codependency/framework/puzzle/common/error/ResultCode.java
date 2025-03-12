package cn.codependency.framework.puzzle.common.error;

/**
 * @author Made Wheels
 * @date 2024-11-28 09:07
 */
public interface ResultCode {

    /**
     * 返回码
     * @return
     */
    public Integer getCode();

    /**
     * 返回描述信息，当返回码是Success时，可以为Null
     * @return
     */
    public String getMessage();

}
