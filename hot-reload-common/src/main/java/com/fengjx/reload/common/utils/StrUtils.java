package com.fengjx.reload.common.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author FengJianxin
 * @since 2021-11-20
 */
public class StrUtils {


    public static String[] trims(String[] src) {
        return Arrays.stream(src).map(String::trim).toArray(String[]::new);
    }


    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StrUtils.isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !StrUtils.isBlank(str);
    }

    public static String join(Object[] parts, String separator) {
        return join(Arrays.asList(parts), separator);
    }

    public static String join(Iterable<? extends Object> parts, String separator) {
        if (parts == null) {
            return "";
        }
        Iterator<?> iterator = parts.iterator();
        StringBuilder sb = new StringBuilder();
        if (iterator.hasNext()) {
            sb.append(toString(iterator.next()));
            while (iterator.hasNext()) {
                sb.append(separator);
                sb.append(toString(iterator.next()));
            }
        }
        return sb.toString();
    }

    private static CharSequence toString(Object part) {
        Objects.requireNonNull(part);
        return part instanceof CharSequence ? (CharSequence) part : part.toString();
    }

}
