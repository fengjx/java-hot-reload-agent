package com.fengjx.reload.server;

import com.fengjx.reload.common.utils.FileUtils;
import com.google.inject.Singleton;
import lombok.Getter;

import java.nio.file.Paths;

/**
 * @author fengjianxin
 */
@Getter
@Singleton
public class ServerConfig {

    private int port = 8080;
    private String targetDir;

    public void init(int port) {
        String targetDir = Paths.get(FileUtils.getTempDirectoryStr(), "target").toString();
        init(port, targetDir);
    }

    public void init(int port, String targetDir) {
        this.port = port;
        this.targetDir = targetDir;
    }

}
