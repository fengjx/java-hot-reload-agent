package com.fengjx.reload.server;

import com.fengjx.reload.server.api.ResponseKit;
import com.fengjx.reload.server.api.Routers;
import lombok.extern.slf4j.Slf4j;

import static spark.Spark.*;

/**
 * @author fengjianxin
 */
@Slf4j
public class Server {

    private void initThreadPool() {
        int maxThreads = 8;
        int minThreads = 2;
        int timeOutMillis = 30000;
        threadPool(maxThreads, minThreads, timeOutMillis);
    }

    private void initRouter() {
        before((request, response) -> {
            response.header("Server", "hot-reload-server");
            response.type("application/json");
        });
        get("/ping", (req, res) -> "hot-reload-server: pong");
        post("/hotReload", Routers::hotReload);
        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            ResponseKit.fail().setMsg(exception.getMessage());
        });
    }

    private void destroy() {
        stop();
    }

    public void start() {
        port(ServerConfig.me().getPort());
        initThreadPool();
        staticFileLocation("static");
        initRouter();
        Runtime.getRuntime().addShutdownHook(new Thread("server-shutdown-hook") {
            @Override
            public void run() {
                Server.this.destroy();
                log.info("server shutdown");
            }
        });
    }

}
