package com.fengjx.reload.watcher.worker;

import com.fengjx.reload.common.AgentConfig;
import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.jvm.ClassUtils;
import com.fengjx.reload.common.jvm.VMUtils;
import com.fengjx.reload.watcher.config.Config;

import java.util.Set;

/**
 * @author fengjianxin
 * @since 2021-12-05
 */
public class LocalWorker implements Worker {

    @Override
    public void doReload(Set<String> files) {
        for (String file : files) {
            reloadClass(getClassName(file), file);
        }
    }

    /**
     * 文件路径转类名
     */
    private String getClassName(String classFilePath) {
        String[] watchPaths = Config.me().getWatchPaths();
        for (String watchPath : watchPaths) {
            if (classFilePath.startsWith(watchPath)) {
                return ClassUtils.fileToClassName(watchPath, classFilePath);
            }
        }
        throw new RuntimeException("Invalid file path: " + classFilePath);
    }

    private synchronized void reloadClass(String className, String classFilePath) {
        AnsiLog.info("Reload class: {}", className);
        int pid = Config.me().getLocal().getPid();
        if (pid == 0) {
            AnsiLog.warn("Pid is null");
            return;
        }
        AnsiLog.debug("Load agent jar: {}", AgentConfig.getAgentJar());
        try {
            VMUtils.reloadClassForLocalVM(String.valueOf(pid), className, classFilePath);
            AnsiLog.info("Reload class[{}] success", className);
        } catch (Exception e) {
            AnsiLog.error("Reload class[{}] error", className);
            AnsiLog.error(e);
        }
    }


}
