package me.keithmo.barrage.mock.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.keithmo.barrage.mock.annotation.IgnoreGlobalSleep;
import me.keithmo.barrage.mock.annotation.IgnoreGlobalSuccessRate;
import me.keithmo.barrage.mock.annotation.RawOutput;
import me.keithmo.barrage.mock.constants.PathConstants;
import me.keithmo.barrage.mock.enums.BaseResultStatus;
import me.keithmo.barrage.mock.model.response.DemoResponse;
import me.keithmo.barrage.mock.util.MockUtil;
import me.keithmo.barrage.mock.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

/**
 * 全局统一包装响应
 *
 * @author keithmo
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    private ObjectMapper jacksonObjectMapper;

    @Value("${mock.response.sleepMs:100}")
    private int sleepMs;
    @Value("${mock.response.successRate:100}")
    private double successRate;

    /**
     * 带 {@link RawOutput} 注解的方法不包装
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(RawOutput.class);
    }

    /**
     * 包装响应体
     */
    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (shouldSkip(body, request)) {
            return body;
        }

        if (shouldReturnError(body, returnType)) {
            return DemoResponse.error(BaseResultStatus.ERROR, body);
        }

        if (sleepMs > 0 && !returnType.hasMethodAnnotation(IgnoreGlobalSleep.class)) {
            MockUtil.sleep(sleepMs);
        }

        // String 类型单独处理
        if (body instanceof String) {
            String strBody;
            try {
                strBody = jacksonObjectMapper.writeValueAsString(DemoResponse.success(body));
            } catch (JsonProcessingException e) {
                log.warn(e.getLocalizedMessage());
                return body;
            }

            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return strBody;
        }

        return DemoResponse.success(body);
    }

    /**
     * 自己组装响应体或返回资源文件的不包装
     */
    private boolean shouldSkip(@Nullable Object body, ServerHttpRequest request) {
        return body instanceof DemoResponse
                || body instanceof Resource
                || request.getURI().getPath().startsWith(PathConstants.ACTUATOR_PATH);
    }

    private boolean shouldReturnError(@Nullable Object body, MethodParameter returnType) {
        return body instanceof DefaultErrorAttributes
                || (
                body instanceof LinkedHashMap<?, ?>
                        && ((LinkedHashMap<?, ?>)body).containsKey("status")
                        && (int)((LinkedHashMap<?, ?>)body).get("status") >= 400)
                || (
                RandomUtil.randomPercent() > successRate
                        && !returnType.hasMethodAnnotation(IgnoreGlobalSuccessRate.class
                )
        );
    }
}
