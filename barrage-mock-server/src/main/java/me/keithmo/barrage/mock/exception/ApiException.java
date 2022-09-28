package me.keithmo.barrage.mock.exception;

import lombok.Getter;
import me.keithmo.barrage.mock.enums.ResultStatus;

/**
 * @author keithmo
 */
@Getter
public class ApiException extends RuntimeException {
    private final String code;
    private final String msg;

    public ApiException(ResultStatus resultStatus) {
        this(resultStatus.getCode(), resultStatus.getMessage());
    }

    public ApiException(ResultStatus resultStatus, String customMessage) {
        this(resultStatus.getCode(), customMessage);
    }

    public ApiException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}

