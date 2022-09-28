package me.keithmo.barrage.mock.handler;

import lombok.extern.slf4j.Slf4j;
import me.keithmo.barrage.mock.annotation.ExceptionCode;
import me.keithmo.barrage.mock.enums.BaseResultStatus;
import me.keithmo.barrage.mock.enums.ResultStatus;
import me.keithmo.barrage.mock.exception.ApiException;
import me.keithmo.barrage.mock.model.response.DemoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author keithmo
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DemoResponse<Object> handle(MethodArgumentNotValidException e) {
        List<ResultStatus> code = getErrorFields(e).stream()
                .map(field -> field.getAnnotation(ExceptionCode.class))
                .filter(Objects::nonNull)
                .map(ExceptionCode::value)
                .collect(Collectors.toList());

        ResultStatus resultCode = BaseResultStatus.VALIDATION_FAILED;
        if (!code.isEmpty()) {
            resultCode = code.get(0);
        }

        return DemoResponse.error(resultCode, getAllErrorMessages(e));
    }

    @ExceptionHandler(ApiException.class)
    public DemoResponse<String> handle(ApiException e) {
        log.error(e.getLocalizedMessage(), e);

        return DemoResponse.error(e.getCode(), e.getMsg(), e.getLocalizedMessage());
    }

    /**
     * 用于处理通用异常
     */
    @ExceptionHandler(BindException.class)
    public DemoResponse<Object> handle(BindException e) {
        String msg = getAllErrorMessages(e);
        log.error(msg);

        return DemoResponse.error(BaseResultStatus.ERROR, msg);
    }

    private List<Field> getErrorFields(MethodArgumentNotValidException e) {
        List<String> fieldNames = getErrorFieldNames(e);

        return Arrays.stream(e.getParameter()
                        .getParameterType()
                        .getDeclaredFields())
                .filter(Objects::nonNull)
                .filter(field -> fieldNames.contains(field.getName()))
                .collect(Collectors.toList());
    }

    private List<String> getErrorFieldNames(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(Objects::nonNull)
                .map(FieldError::getField)
                .collect(Collectors.toList());
    }

    private String getAllErrorMessages(BindException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return String.format("<%s> in <%s> %s",
                                ((FieldError)error).getField(), error.getObjectName(), error.getDefaultMessage());
                    }

                    return String.format("<%s> %s", error.getObjectName(), error.getDefaultMessage());
                })
                .collect(Collectors.joining(";"));
    }
}
