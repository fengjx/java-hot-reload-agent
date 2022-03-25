package com.fengjx.reload.watcher.config;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.IOUtils;
import com.fengjx.reload.common.utils.JsonUtils;
import com.fengjx.reload.common.utils.PropUtils;
import com.fengjx.reload.common.utils.StrUtils;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * @author fengjianxin
 */
@Data
@Getter
public class Config {

    private String mode;
    private int pid;
    private String[] watchPaths;
    private ServerConfig server;

    private Config() {
    }

    public static Config newInstance(String configPath) throws IOException {
        Config config = new Config();
        if (StrUtils.isNotBlank(configPath)) {
            config = JsonUtils.fromJson(IOUtils.toString(IOUtils.toInputStream(configPath)), Config.class);
        }
        config.setMode(PropUtils.getProp("watcher.mode", config.getMode()));
        config.setPid(Integer.parseInt(PropUtils.getProp("watcher.pid", config.getPid() + "")));
        if (config.getWatchPaths() != null) {
            config.watchPaths = config.getWatchPaths();
        } else {
            String paths = PropUtils.getProp("watcher.paths");
            if (StrUtils.isBlank(paths)) {
                throw new InvalidParameterException("[watchPaths] 未指定");
            }
            config.watchPaths = StrUtils.trims(paths.split(","));
        }
        ServerConfig server = new ServerConfig();
        if (config.getServer() != null) {
            server = config.getServer();
        }
        String serverHost = PropUtils.getProp("watcher.server.host");
        if (StrUtils.isNotBlank(serverHost)) {
            server.setHost(serverHost);
        }
        config.setServer(server);
        AnsiLog.info("config: {}", JsonUtils.toPrettyJson(config));
        return config;
    }

    public boolean isLocalMode() {
        return "local".equalsIgnoreCase(getMode());
    }

}
