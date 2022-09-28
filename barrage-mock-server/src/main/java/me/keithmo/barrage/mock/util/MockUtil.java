package me.keithmo.barrage.mock.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

/**
 * @author keithmo
 */
@UtilityClass
public class MockUtil {
    public static void sleep(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
