package com.zcx.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceUtils {
    private ServiceUtils() {}

    public static void rollback(String msg) {
        throw new RuntimeException(msg);
    }
    public static Map pageResult(Object data, Integer totalRows) {
        Map result = new HashMap<>();
        result.put("pagedata", data);
        result.put("totalRows", totalRows);
        return result;
    }
}
