package me.keithmo.barrage.mock.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author keithmo
 */
@UtilityClass
public class RandomUtil {
    private static final Random RAND = ThreadLocalRandom.current();

    public static int randomInt(int bound) {
        return RAND.nextInt(bound);
    }

    public static double randomPercent() {
        return RAND.nextInt(100000 + 1) / 1000d;
    }

    public static int randomElement(List<Integer> list) {
        return list.get(randomInt(list.size()));
    }
}
