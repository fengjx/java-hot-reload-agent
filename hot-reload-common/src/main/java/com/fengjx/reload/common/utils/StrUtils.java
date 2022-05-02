package com.fengjx.reload.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @author FengJianxin
 * @since 2021-11-20
 */
public class StrUtils {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

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

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(AB.charAt(ThreadLocalRandom.current().nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            int pos = 0;
            int index = inString.indexOf(oldPattern);
            if (index < 0) {
                //no need to replace
                return inString;
            }

            StringBuilder sb = new StringBuilder();
            for (int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                sb.append(inString, pos, index);
                sb.append(newPattern);
                pos = index + patLen;
            }

            sb.append(inString.substring(pos));
            return sb.toString();
        } else {
            return inString;
        }
    }

    public static List<String> toLines(String text) {
        List<String> result = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            String line = reader.readLine();
            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
        } catch (IOException exc) {
            // quit
        }
        // ignore
        return result;
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

}
