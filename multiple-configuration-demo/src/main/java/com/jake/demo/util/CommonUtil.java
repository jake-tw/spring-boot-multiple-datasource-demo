package com.jake.demo.util;

import java.util.UUID;

public class CommonUtil {
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid.toUpperCase();
    }
}
