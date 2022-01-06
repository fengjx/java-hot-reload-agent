package com.fengjx.reload.server.service;


import com.fengjx.reload.common.AgentConfig;
import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.jvm.VMUtils;
import com.fengjx.reload.common.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fengjianxin
 */
@Slf4j
public class HotReloadService {


    private HotReloadService() {
    }

    private static final HotReloadService INSTANCE = new HotReloadService();

    public static HotReloadService me() {
        return INSTANCE;
    }


    public void reloadClass(String pid, String className, String classFilePath) throws Exception {
        log.info("Reload class: {}", className);
        if (StrUtils.isBlank(pid)) {
            AnsiLog.warn("Pid is null");
            return;
        }
        log.debug("Load agent jar: {}", AgentConfig.getAgentJar());
        VMUtils.reloadClassForLocalVM(pid, className, classFilePath);
    }


}
