package com.fengjx.reload.common;

import com.fengjx.reload.common.utils.StrUtils;

import java.io.File;

/**
 * @author FengJianxin
 * @since 2021-11-20
 */
public class AgentConfig {

    private static final String AGENT_HOME_PARAM = "agent_home";

    public static String getHomeDir() {
        String home = System.getProperty(AGENT_HOME_PARAM);
        if (StrUtils.isBlank(home)) {
            home = System.getenv(AGENT_HOME_PARAM);
        }
        if (StrUtils.isBlank(home)) {
            home = System.getProperty("user.home") + File.separator + ".hot-reload-agent";
        }
        return home;
    }

    public static String getAgentJar() {
        return getHomeDir() + File.separator + "hot-reload-agent.jar";
    }

    public static String getCoreJarPath() {
        return getHomeDir() + File.separator + "hot-reload-core.jar";
    }

    public static File getCoreJarFile() {
        return new File(getCoreJarPath());
    }


}
