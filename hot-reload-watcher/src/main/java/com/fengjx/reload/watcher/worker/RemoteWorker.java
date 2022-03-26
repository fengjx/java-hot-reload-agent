package com.fengjx.reload.watcher.worker;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.consts.FileExtension;
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
            reloadClass(file);
        }
    }

    /**
     * 向远程 server 提交需要重新加载的文件
     *
     * @param targetFilePath
     */
    private synchronized void reloadClass(String targetFilePath) {
        AnsiLog.info("reload class: {}", targetFilePath);
        int pid = config.getPid();
        if (pid == 0) {
            AnsiLog.warn("Pid is null");
            return;
        }
        ServerConfig server = config.getServer();
        String extension = FileExtension.CLASS_FILE_EXT;
        if (targetFilePath.endsWith(FileExtension.JAVA_FILE_EXTENSION)) {
            extension = FileExtension.JAVA_FILE_EXT;
        }
        Map<String, String> params = new HashMap<>(8);
        params.put("pid", String.valueOf(pid));
        params.put("extension", extension);
        File targetFile = new File(targetFilePath);
        String res = HttpUtils.upload(server.getHotReloadApi(), targetFile, params);
        Type type = new TypeToken<Result<Map<String, Object>>>() {
        }.getType();
        Result<Map<String, Object>> result = JsonUtils.fromJson(res, type);
        if (result.getCode() == 200) {
            AnsiLog.info("reload [{}] success", targetFile.getName());
        } else {
            AnsiLog.error("reload [{}] error: {}", targetFile.getName(), res);
        }
    }


}
