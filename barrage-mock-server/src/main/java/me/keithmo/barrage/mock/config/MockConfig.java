package me.keithmo.barrage.mock.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author keithmo
 */
@ConfigurationProperties("mock")
@Data
@RequiredArgsConstructor
public class MockConfig {
    @Value("${mock.response.sleep:100}")
    private Duration sleep;

    @Value("${mock.response.successRate:100}")
    private double successRate;

    public MockConfig getInstance() {
        return this;
    }
}
