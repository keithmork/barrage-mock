package me.keithmo.barrage.mock.domain.demo.service.impl;

import me.keithmo.barrage.mock.api.DemoService;
import me.keithmo.barrage.mock.model.response.DemoResponse;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author keithmo
 */
@DubboService
public class DemoServiceImpl implements DemoService {

    /**
     * Hello world
     *
     * @param name Any string
     * @return Hello + name
     */
    @Override
    public DemoResponse<String> sayHello(String name) {
        return DemoResponse.success(String.format("Hello %s!", name));
    }

    /**
     * Get server timestamp
     *
     * @return 13-digit timestamp
     */
    @Override
    public DemoResponse<Long> getTime() {
        return DemoResponse.success(System.currentTimeMillis());
    }
}
