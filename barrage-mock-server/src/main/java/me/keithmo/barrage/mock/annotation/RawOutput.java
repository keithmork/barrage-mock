package me.keithmo.barrage.mock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口加上这个就能自定义响应体，忽略统一包装设置。
 *
 * @author keithmo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RawOutput {
}
