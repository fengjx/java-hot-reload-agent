package com.fengjx.reload.common.jvm;

import com.fengjx.reload.common.AgentConfig;
import com.fengjx.reload.common.utils.StrUtils;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import lombok.experimental.UtilityClass;

import java.io.IOException;

/**
 * @author fengjianxin
 */
@UtilityClass
public class VMUtils {

    /**
     * 加载 class 到本地 jvm
     * @param pid 进程号
     * @param className 全路径类名
     * @param classFilePath class 文件物理路径
     */
    public static void reloadClassForLocalVM(String pid, String className, String classFilePath) throws
            IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        if (StrUtils.isBlank(pid)) {
            return;
        }
        VirtualMachine attach = null;
        try {
            attach = VirtualMachine.attach(pid);
            attach.loadAgent(AgentConfig.getAgentJar(), className + "," + classFilePath);
        } finally {
            if (attach != null) {
                attach.detach();
            }
        }
    }



}
