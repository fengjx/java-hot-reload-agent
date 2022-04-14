package com.fengjx.reload.watcher.utils;

import com.fengjx.reload.common.AnsiLog;
import lombok.experimental.UtilityClass;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author fengjianxin
 */
@UtilityClass
public class HttpUtils {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data");

    public static final OkHttpClient CLIENT = new OkHttpClient();

    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request(request);
    }

    public static String post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request(request);
    }

    public static String upload(String url, File file, Map<String, String> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE, file))
                .build();

        Request request = new Request.Builder().url(url)
                .post(requestBody).build();

        return request(request);
    }

    private static String request(Request request) {
        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                AnsiLog.error(response.toString());
            }
            return response.body() == null ? "" : response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
