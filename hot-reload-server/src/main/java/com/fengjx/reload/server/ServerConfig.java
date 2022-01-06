package com.fengjx.reload.server;

import lombok.Getter;

/**
 * @author fengjianxin
 */
@Getter
public class ServerConfig {

    private int port = 8080;
    private String tmpDir = "/temp/hot-reload-server/tmp";
    private String targetDir = "/temp/hot-reload-server/target";

    private ServerConfig() {
    }

    private static final ServerConfig INSTANCE = new ServerConfig();

    public static void init(int port) {
        INSTANCE.port = port;
    }

    public static void init(int port, String tmpDir, String targetDir) {
        INSTANCE.port = port;
        INSTANCE.tmpDir = tmpDir;
        INSTANCE.targetDir = targetDir;
    }

    public static ServerConfig me() {
        return INSTANCE;
    }


}
