package com.fengjx.reload.server.service;


import com.fengjx.reload.common.AgentConfig;
import com.fengjx.reload.common.jvm.VMUtils;
import com.fengjx.reload.common.utils.StrUtils;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fengjianxin
 */
@Slf4j
@Singleton
public class HotReloadService {

    public void reloadClass(String pid, String className, String classFilePath) throws Exception {
        log.info("reload class: {}", className);
        if (StrUtils.isBlank(pid)) {
            log.warn("pid is null");
            return;
        }
        log.debug("load agent jar: {}", AgentConfig.getAgentJar());
        VMUtils.reloadClassForLocalVM(pid, className, classFilePath);
    }


}
