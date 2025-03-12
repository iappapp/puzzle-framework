package cn.codependency.framework.puzzle.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Result<Data> implements Serializable {

    private String code;

    private String message;

    private Boolean success;

    private String traceId;

    private Data data;

    public static <Data> Result<Data> success() {
        return success(null);
    }

    public static <Data> Result<Data> success(Data data) {
        return success(data, "请求成功");
    }

    public static <Data> Result<Data> success(Data data, String message) {
        Result<Data> result = new Result<Data>();
        result.success = true;
        result.code = "SUCCESS";
        result.data = data;
        result.message = message;
        return result;
    }


    public static <Data> Result<Data> fail(String message) {
        return fail("FAIL", message);
    }

    public static <Data> Result<Data> fail(String code, String message) {
        Result<Data> result = new Result<Data>();
        result.success = false;
        result.code = code;
        result.message = message;
        return result;
    }

    public static <Data> Result<Data> fail(String code, String message, String traceId) {
        Result<Data> result = new Result<Data>();
        result.success = false;
        result.code = code;
        result.message = message;
        result.traceId = traceId;
        return result;
    }
}
