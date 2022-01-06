package com.fengjx.reload.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengjianxin
 */
@UtilityClass
public class JsonUtils {

    private static final Gson GSON = new Gson();

    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }

    public static String toPrettyJson(Object src) {
        return PRETTY_GSON.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        return GSON.fromJson(json, cls);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static Map<String, Object> toMap(String json) {
        if (StrUtils.isBlank(json)) {
            return new HashMap<>();
        }
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return GSON.fromJson(json, type);
    }


}
