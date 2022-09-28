package me.keithmo.barrage.mock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author keithmo
 */

@Getter
@AllArgsConstructor
public enum BaseResultStatus implements ResultStatus {
    /**
     * Common
     */
    SUCCESS("0", "success"),
    ERROR("1", "error"),

    VALIDATION_FAILED("10000", "参数校验错误"),
    SERVICE_ERROR("11000", "接口调用异常"),
    MOCK_ERROR("20000", "模拟请求失败；全局成功率设置见 mock.response.successRate"),
    ;

    private final String code;
    private final String message;
}
