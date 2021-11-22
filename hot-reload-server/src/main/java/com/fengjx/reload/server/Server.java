package com.fengjx.reload.server;

import com.fengjx.reload.server.api.Routers;
import lombok.extern.slf4j.Slf4j;

import static spark.Spark.*;

@Slf4j
public class Server {

    private void initThreadPool() {
        int maxThreads = 8;
        int minThreads = 2;
        int timeOutMillis = 30000;
        threadPool(maxThreads, minThreads, timeOutMillis);
    }

    private void initRouter() {
        before((request, response) -> response.header("Server", "hot-reload-server"));
        get("/ping", (req, res) -> "hot-reload-server: pong");
        post("/hotfix", Routers::hotfix);
    }

    private void destroy() {
        stop();
    }

    public void start() {
        port(8080);
        initThreadPool();
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
