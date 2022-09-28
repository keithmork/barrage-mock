package me.keithmo.barrage.mock.api;

import me.keithmo.barrage.mock.model.response.DemoResponse;

/**
 * @author keithmo
 */
public interface DemoService {
    /**
     * Hello world
     *
     * @param name Any string
     * @return Hello + name
     */
    DemoResponse<String> sayHello(String name);

    /**
     * Get server timestamp
     *
     * @return 13-digit timestamp
     */
    DemoResponse<Long> getTime();
}
