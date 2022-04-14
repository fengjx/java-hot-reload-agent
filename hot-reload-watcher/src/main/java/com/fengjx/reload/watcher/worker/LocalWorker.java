package com.fengjx.reload.watcher.worker;

import com.fengjx.reload.common.AgentConfig;
import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.jvm.VMUtils;
import com.fengjx.reload.common.utils.ProcessUtils;
import com.fengjx.reload.watcher.config.Config;
import com.google.inject.Inject;

import java.util.Map;
import java.util.Set;

/**
 * @author fengjianxin
 * @since 2021-12-05
 */
public class LocalWorker implements Worker {

    @Inject
    private Config config;

    @Override
    public Map<Long, String> jps() {
        return ProcessUtils.listProcessByJps(false);
    }

    @Override
    public void doReload(Set<String> files) {
        for (String file : files) {
            reloadClass(file);
        }
    }

    /**
     * 重新加载 class 到本地 jvm
     *
     * @param targetFilePath .class or .java
     */
    private void reloadClass(String targetFilePath) {
        AnsiLog.info("reload class: {}", targetFilePath);
        int pid = config.getPid();
        if (pid == 0) {
            AnsiLog.warn("pid is null");
            return;
        }
        AnsiLog.debug("load agent jar: {}", AgentConfig.getAgentJar());
        try {
            VMUtils.reloadClassForLocalVM(String.valueOf(pid), targetFilePath);
            AnsiLog.info("reload class[{}] success", targetFilePath);
        } catch (Exception e) {
            AnsiLog.error("reload class[{}] error", targetFilePath);
            AnsiLog.error(e);
        }
    }


}
