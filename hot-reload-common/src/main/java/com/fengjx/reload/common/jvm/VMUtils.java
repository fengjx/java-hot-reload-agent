package com.fengjx.reload.common.jvm;

import com.fengjx.reload.common.AgentConfig;
import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.StrUtils;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

/**
 * @author fengjianxin
 */
@UtilityClass
public class VMUtils {

    /**
     * 加载 class 到本地 jvm
     *
     * @param pid            进程号
     * @param targetFilePath class 文件物理路径
     */
    public static void reloadClassForLocalVM(String pid, String targetFilePath) throws
            IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        if (StrUtils.isBlank(pid)) {
            return;
        }
        VirtualMachine attach = null;
        try {
            attach = VirtualMachine.attach(pid);
            String agentJar = AgentConfig.getAgentJar();
            File jarfile = new File(agentJar);
            if (!jarfile.exists()) {
                AnsiLog.error("agent jar [{}] not found, see com.fengjx.reload.common.AgentConfig#getAgentJar", agentJar);
                return;
            }
            attach.loadAgent(AgentConfig.getAgentJar(), targetFilePath);
        } finally {
            if (attach != null) {
                attach.detach();
            }
        }
    }


}
