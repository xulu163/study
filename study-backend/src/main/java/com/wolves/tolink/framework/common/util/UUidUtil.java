package com.wolves.tolink.framework.common.util;

import java.util.UUID;

public class UUidUtil {

    public static String generateUuid(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}
