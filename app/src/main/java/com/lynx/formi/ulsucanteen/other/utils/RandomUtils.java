package com.lynx.formi.ulsucanteen.other.utils;

import java.util.Random;

public class RandomUtils {

    public static String generateRandomKey() {
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXY0123456789";

        for (int i = 0; i < 8; i++) {
            int randomNum = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(randomNum));
        }

        return result.toString();
    }

}
