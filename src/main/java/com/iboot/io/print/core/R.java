package com.iboot.io.print.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private boolean success;
    private int code;
    private String msg;
    private String detailMsg;
    private T data;

    public static <T> R<T> ok(T data) {
        return new R<>(true, 200, "success", null, data);
    }

    public static <T> R<T> ok() {
        return new R<>(true, 200, "success", null, null);
    }

    public static <T> R<T> fail(String msg, String detailMsg) {
        return new R<>(false, 500, msg, detailMsg, null);
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(false, 500, msg, null, null);
    }

    public static <T> R<T> fail() {
        return new R<>(false, 500, "error", null, null);
    }
}