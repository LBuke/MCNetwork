package me.lukebingham.util;

import java.util.Random;

/**
 * Created by LukeBingham on 08/04/2017.
 */
public class RandomUtil {
    private static final Random RANDOM;

    static {
        RANDOM = new Random();
    }

    public static Random getRandom() {
        return RANDOM;
    }

    public static int getRandomInt(int input) {
        return RANDOM.nextInt(input);
    }
}
