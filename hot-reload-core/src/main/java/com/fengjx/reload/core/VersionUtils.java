package com.fengjx.reload.core;

/**
 * @author FengJianxin
 * @since 2021-11-12
 */
public class VersionUtils {

    private static final String VERSION_PROPERTY_KEY = "java.specification.version";
    private static final String JAVA_VERSION_STR = System.getProperty(VERSION_PROPERTY_KEY);

    public static boolean isJava6() {
        return JAVA_VERSION_STR.equals("1.6");
    }

    public static boolean isJava7() {
        return JAVA_VERSION_STR.equals("1.7");
    }

    public static boolean isJava8() {
        return JAVA_VERSION_STR.equals("1.8");
    }

    public static boolean isJava9() {
        return JAVA_VERSION_STR.equals("9.0");
    }

    public static boolean isLessThanJava9() {
        return Float.parseFloat(JAVA_VERSION_STR) < 9;
    }
}
