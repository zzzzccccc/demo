package com.zcx.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

public class ControllerUtils {
    @Getter
    @Setter
    static class Page<T> {
        private T conditions;
        private Integer currentPage;
        private Integer rows;
    }

    private ControllerUtils() {}
    public static String errorMsg(List<ObjectError> allErrors) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allErrors.size(); i++) {
            if (i > 0) {
                sb.append("+");
            }
            ObjectError allError = allErrors.get(i);
            sb.append("field: ").append(Arrays.toString(allError.getArguments())).append("_msg: ").append(allError.getDefaultMessage());
        }
        return sb.toString();
    }
}
