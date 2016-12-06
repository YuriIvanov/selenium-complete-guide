package ru.yurivan.selenium.litecart.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Utils for generating different kind of test data.
 */
public class Generators {
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generate random alphanumeric string.
     *
     * @param prefix  string that will prepend random part.
     * @param postfix string that will be placed after random part.
     * @return randomly generated string with specified prefix and postfix.
     */
    public static String randomString(String prefix, String postfix) {
        return randomString(prefix, postfix, 64);
    }

    /**
     * Generate random alphanumeric string.
     *
     * @param prefix  string that will prepend random part.
     * @param postfix string that will be placed after random part.
     * @param numBits how long and "strong" should be random part of the string.
     * @return randomly generated string with specified prefix and postfix.
     */
    public static String randomString(String prefix, String postfix, int numBits) {
        return prefix + new BigInteger(numBits, secureRandom).toString(Character.MAX_RADIX) + postfix;
    }
}
