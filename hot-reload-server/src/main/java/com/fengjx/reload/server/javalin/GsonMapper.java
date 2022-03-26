package com.fengjx.reload.server.javalin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.plugin.json.JsonMapper;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author FengJianxin
 * @since 2022/3/25
 */
public class GsonMapper implements JsonMapper {

    private static final Gson GSON = new GsonBuilder().create();

    @NotNull
    @Override
    public String toJsonString(@NotNull Object obj) {
        return GSON.toJson(obj);
    }

    @NotNull
    @Override
    public InputStream toJsonStream(@NotNull Object obj) {
        return new ByteArrayInputStream(toJsonString(obj).getBytes(StandardCharsets.UTF_8));
    }

    @NotNull
    @Override
    public <T> T fromJsonString(@NotNull String json, @NotNull Class<T> targetClass) {
        return GSON.fromJson(json, targetClass);
    }

    @NotNull
    @Override
    public <T> T fromJsonStream(@NotNull InputStream json, @NotNull Class<T> targetClass) {
        return GSON.fromJson(new InputStreamReader(json), targetClass);
    }
}
