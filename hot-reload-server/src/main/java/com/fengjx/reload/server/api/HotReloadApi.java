package com.fengjx.reload.server.api;

import com.fengjx.reload.common.utils.FileUtils;
import com.fengjx.reload.common.utils.IOUtils;
import com.fengjx.reload.common.utils.StrUtils;
import com.fengjx.reload.server.ServerConfig;
import com.fengjx.reload.server.service.HotReloadService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * @author FengJianxin
 * @since 2022-03-05
 */
@Slf4j
@Singleton
public class HotReloadApi implements Router {

    @Inject
    private ServerConfig config;
    @Inject
    private HotReloadService hotReloadService;

    @Override
    public void bind(Javalin app) {
        app.post("/hotReload", this::hotReload);
    }

    public void hotReload(Context ctx) {
        String pid = ctx.formParam("pid");
        String extension = ctx.formParam("extension");
        String targetFilePath = buildFilePath(pid, extension);
        UploadedFile uploadedFile = ctx.uploadedFile("file");
        if (uploadedFile == null) {
            ctx.json(ResponseKit.fail().setMsg("file is empty"));
            return;
        }
        try (InputStream is = uploadedFile.getContent()) {
            copyFile(is, targetFilePath);
            hotReloadService.reloadClass(pid, targetFilePath);
            ctx.json(ResponseKit.ok());
        } catch (Exception e) {
            log.error("hotReload error", e);
            ctx.status(500);
            ctx.json(ResponseKit.fail().setMsg(e.getMessage()));
        } finally {
            FileUtils.delete(targetFilePath);
            log.info("delete file: {}", targetFilePath);
        }
    }

    private void copyFile(InputStream is, String targetFilePath) throws IOException {
        File file = new File(targetFilePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        IOUtils.copy(is, fos);
    }

    private String buildFilePath(String pid, String extension) {
        String targetDir = config.getTargetDir();
        return Paths.get(targetDir, pid, StrUtils.randomString(10), "replace_" + StrUtils.randomString(4) + "." + extension).toString();
    }

}
