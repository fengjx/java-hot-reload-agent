package com.fengjx.reload.server;

import com.fengjx.reload.common.utils.PropUtils;

/**
 * @author fengjianxin
 */
public class ServerBootstrap {

    public static void main(String[] args) {
        int port = Integer.parseInt(PropUtils.getProp("server.port", "8080"));
        ServerConfig.init(port);
        new Server().start();
    }


}
