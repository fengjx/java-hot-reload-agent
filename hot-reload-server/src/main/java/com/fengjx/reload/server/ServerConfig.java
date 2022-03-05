package com.fengjx.reload.server;

import com.google.inject.Singleton;
import lombok.Getter;

/**
 * @author fengjianxin
 */
@Getter
@Singleton
public class ServerConfig {

    private int port = 8080;
    private String tmpDir = "/temp/hot-reload-server/tmp";
    private String targetDir = "/temp/hot-reload-server/target";

    public void init(int port) {
        this.port = port;
    }

    public void init(int port, String tmpDir, String targetDir) {
        this.port = port;
        this.tmpDir = tmpDir;
        this.targetDir = targetDir;
    }

}
