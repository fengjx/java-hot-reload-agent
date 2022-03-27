package com.fengjx.reload.watcher.config;

import lombok.Data;

/**
 * @author fengjianxin
 */
@Data
public class ServerConfig {

    private static final String PROTOCOL = "http://";

    private String host;

    public String getHotReloadApi() {
        return PROTOCOL + host + "/hotReload";
    }

    public String getProcessListApi() {
        return PROTOCOL + host + "/process/list";
    }

}
