package com.fengjx.reload.server;

import com.fengjx.reload.server.api.Routers;

import static spark.Spark.*;

/**
 * @author fengjianxin
 */
public class ServerBootstrap {

    public static void main(String[] args) {
        port(8080);
        connect("/ping", (req, res) -> "hot-reload-server: pong");
        post("/hotfix", Routers.hotfix());
    }

}
