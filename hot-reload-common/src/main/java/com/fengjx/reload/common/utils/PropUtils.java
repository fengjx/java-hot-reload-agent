package com.fengjx.reload.common.utils;

import lombok.experimental.UtilityClass;

/**
 * @author fengjianxin
 */
@UtilityClass
public class PropUtils {

    public static String getProp(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (StrUtils.isBlank(value)) {
            value = System.getenv(key);
        }
        return value == null ? defaultValue : value;
    }

    public static String getProp(String key) {
        return getProp(key, null);
    }

}
