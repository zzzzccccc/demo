package com.zcx.utils;

import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private TokenUtils() {}

    public static Map<String, Object> getHeaderClaims() {
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        return headerClaims;
    }
}
