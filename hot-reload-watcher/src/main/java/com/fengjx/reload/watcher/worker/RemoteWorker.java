package com.fengjx.reload.watcher.worker;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.consts.FileExtension;
import com.fengjx.reload.common.jvm.ClassUtils;
import com.fengjx.reload.common.proto.Result;
import com.fengjx.reload.common.utils.JsonUtils;
import com.fengjx.reload.watcher.config.Config;
import com.fengjx.reload.watcher.config.ServerConfig;
import com.fengjx.reload.watcher.utils.HttpUtils;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fengjianxin
 * @since 2021-12-05
 */
public class RemoteWorker implements Worker {

    @Inject
    private Config config;

    @Override
    public void doReload(Set<String> files) {
        for (String file : files) {
            reloadClass(fileToClassName(file), file);
        }
    }

    /**
     * 文件路径转类名
     */
    private String fileToClassName(String classFilePath) {
        String[] watchPaths = config.getWatchPaths();
        for (String watchPath : watchPaths) {
            if (classFilePath.startsWith(watchPath)) {
                return ClassUtils.fileToClassName(watchPath, classFilePath);
            }
        }
        throw new RuntimeException("Invalid file path: " + classFilePath);
    }

    private synchronized void reloadClass(String className, String filePath) {
        AnsiLog.info("Reload class: {}", className);
        int pid = config.getPid();
        if (pid == 0) {
            AnsiLog.warn("Pid is null");
            return;
        }
        ServerConfig server = config.getServer();
        String extension = FileExtension.CLASS_FILE_EXT;
        if (filePath.endsWith(FileExtension.JAVA_FILE_EXTENSION)) {
            extension = FileExtension.JAVA_FILE_EXT;
        }
        Map<String, String> params = new HashMap<>(8);
        params.put("pid", String.valueOf(pid));
        params.put("className", className);
        params.put("extension", extension);
        String res = HttpUtils.upload(server.getHotReloadApi(), new File(filePath), params);
        Type type = new TypeToken<Result<Map<String, Object>>>() {
        }.getType();
        Result<Map<String, Object>> result = JsonUtils.fromJson(res, type);
        if (result.getCode() == 200) {
            AnsiLog.info("Reload class[{}] success", className);
        } else {
            AnsiLog.error("Reload class[{}] error: {}", className, res);
        }
    }


}
