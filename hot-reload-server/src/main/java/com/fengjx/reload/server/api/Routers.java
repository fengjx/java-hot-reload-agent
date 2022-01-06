package com.fengjx.reload.server.api;

import com.fengjx.reload.common.consts.FileExtension;
import com.fengjx.reload.common.jvm.ClassUtils;
import com.fengjx.reload.common.utils.IOUtils;
import com.fengjx.reload.server.ServerConfig;
import com.fengjx.reload.server.service.HotReloadService;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author fengjianxin
 */
@Slf4j
public class Routers {

    public static Object hotReload(Request req, Response resp) {
        String tmpdir = ServerConfig.me().getTmpDir();
        String targetDir = ServerConfig.me().getTargetDir();
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(tmpdir));
        String pid = req.params("pid");
        String className = req.params("className");
        String extension = req.params("extension");
        String targetFilePath = ClassUtils.classNameToPath(targetDir, className, FileExtension.CLASS_FILE_EXT.equals(extension));
        log.info("reload class: {}, {}", pid, className);
        try (InputStream is = req.raw().getPart("file").getInputStream()) {
            IOUtils.copy(is, new FileOutputStream(targetFilePath));
            HotReloadService.me().reloadClass(pid, className, targetFilePath);
        } catch (Exception e) {
            log.error("hotReload error", e);
            resp.status(500);
            return "";
        }
        return ResponseKit.ok();
    }


}
