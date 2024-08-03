package com.authserver.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/*
 * 1000 ~ 9999 임의의 코드를 얻는 유틸
 */
public final class GenerateCodeUtil {

    private GenerateCodeUtil() {}

    public static String generateCode() {

        String code;

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            code = String.valueOf(random.nextInt(9000) + 1000);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code.");
        }

        return code;
    }
}