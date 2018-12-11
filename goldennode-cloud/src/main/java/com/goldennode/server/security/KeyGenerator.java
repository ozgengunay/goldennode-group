package com.goldennode.server.security;

import java.util.Random;

public class KeyGenerator {
    public static String generateApiKey() {
        return createRandomString(16);
    }

    public static String generateSecretKey() {
        return createRandomString(32);
    }

    public static String createRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateApiKey());
        System.out.println(generateSecretKey());
    }
}
