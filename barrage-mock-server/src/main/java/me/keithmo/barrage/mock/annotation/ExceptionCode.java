package me.keithmo.barrage.mock.annotation;

import me.keithmo.barrage.mock.enums.BaseResultStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来在 DTO 里自定义参数校验错误码
 * @author keithmo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExceptionCode {
    BaseResultStatus value() default BaseResultStatus.ERROR;
}
