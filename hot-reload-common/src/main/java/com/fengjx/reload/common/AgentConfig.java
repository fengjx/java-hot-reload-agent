package com.fengjx.reload.common;

import com.fengjx.reload.common.utils.PropUtils;
import com.fengjx.reload.common.utils.StrUtils;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author FengJianxin
 * @since 2021-11-20
 */
public class AgentConfig {

    private static final String AGENT_HOME_PARAM = "AGENT_HOME";

    public static String getHomeDir() {
        String home = System.getProperty(AGENT_HOME_PARAM);
        if (StrUtils.isBlank(home)) {
            home = System.getenv(AGENT_HOME_PARAM);
        }
        if (StrUtils.isBlank(home)) {
            home = Paths.get(System.getProperty("user.home"), ".hot-reload-agent").toString();
        }
        return home;
    }

    public static String getAgentJar() {
        return Paths.get(getHomeDir(), "hot-reload-agent.jar").toString();
    }

    public static String getCoreJarPath() {
        return Paths.get(getHomeDir(), "hot-reload-core.jar").toString();
    }

    public static File getCoreJarFile() {
        return new File(getCoreJarPath());
    }

    public static boolean isDisableSubClass() {
        return "true".equals(PropUtils.getProp("AGENT_DISABLE_SUBCLASS"));
    }

}
