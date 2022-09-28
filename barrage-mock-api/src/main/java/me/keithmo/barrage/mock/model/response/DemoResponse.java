package me.keithmo.barrage.mock.model.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import me.keithmo.barrage.mock.enums.BaseResultStatus;
import me.keithmo.barrage.mock.enums.ResultStatus;

import java.io.Serializable;

/**
 * @author keithmo
 */
@Getter
@ToString
@EqualsAndHashCode
public class DemoResponse<T> implements Serializable {
    /**
     * 业务返回码
     */
    private final String code;

    private final String message;

    private final T data;

    private DemoResponse(ResultStatus statusCode, T data) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }

    private DemoResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean checkSuccess() {
        return this.code.equalsIgnoreCase(BaseResultStatus.SUCCESS.getCode());
    }

    /**
     * Success
     */
    public static <T> DemoResponse<T> success() {
        return new DemoResponse<>(BaseResultStatus.SUCCESS, null);
    }

    public static <T> DemoResponse<T> success(T data) {
        return new DemoResponse<>(BaseResultStatus.SUCCESS, data);
    }

    /**
     * Fail
     */
    public static <T> DemoResponse<T> error() {
        return new DemoResponse<>(BaseResultStatus.ERROR, null);
    }

    public static <T> DemoResponse<T> error(T data) {
        return new DemoResponse<>(BaseResultStatus.ERROR, data);
    }

    public static <T> DemoResponse<T> error(ResultStatus statusCode) {
        return new DemoResponse<>(statusCode, null);
    }

    public static <T> DemoResponse<T> error(ResultStatus statusCode, T data) {
        return new DemoResponse<>(statusCode, data);
    }

    public static <T> DemoResponse<T> error(String code, String message, T data) {
        return new DemoResponse<>(code, message, data);
    }
}
