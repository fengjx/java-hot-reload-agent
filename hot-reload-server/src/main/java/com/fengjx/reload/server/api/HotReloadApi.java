package com.fengjx.reload.server.api;

import com.fengjx.reload.common.consts.FileExtension;
import com.fengjx.reload.common.jvm.ClassUtils;
import com.fengjx.reload.common.utils.FileUtils;
import com.fengjx.reload.common.utils.IOUtils;
import com.fengjx.reload.server.ServerConfig;
import com.fengjx.reload.server.service.HotReloadService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

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
        String targetDir = config.getTargetDir();
        String pid = ctx.formParam("pid");
        String className = ctx.formParam("className");
        String extension = ctx.formParam("extension");
        String targetFilePath = ClassUtils.classNameToPath(targetDir, className, FileExtension.CLASS_FILE_EXT.equals(extension));
        log.info("reload class: {}, {}", pid, className);

        UploadedFile uploadedFile = ctx.uploadedFile("file");
        if (uploadedFile == null) {
            ctx.json(ResponseKit.fail("file is empty"));
            return;
        }
        try (InputStream is = uploadedFile.getContent()) {
            copyFile(is, targetFilePath);
            hotReloadService.reloadClass(pid, className, targetFilePath);
            ctx.json(ResponseKit.ok());
        } catch (Exception e) {
            log.error("hotReload error", e);
            ctx.status(500);
            ctx.json(ResponseKit.fail());
        } finally {
            FileUtils.delete(targetFilePath);
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


}
