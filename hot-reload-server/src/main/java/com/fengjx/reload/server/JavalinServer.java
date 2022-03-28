package com.fengjx.reload.server;

import com.fengjx.reload.common.utils.PropUtils;
import com.fengjx.reload.server.api.ResponseKit;
import com.fengjx.reload.server.api.Router;
import com.google.inject.Inject;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author fengjianxin
 */
@Slf4j
public class JavalinServer implements Server {

    @Inject
    private Javalin app;
    @Inject
    private ServerConfig config;
    @Inject
    private Set<Router> routers;

    private void init() {
        log.info("hot-reload-server init");
        int port = Integer.parseInt(PropUtils.getProp("server.port", "8000"));
        config.init(port);
    }

    private void initRouter() {
        log.info("hot-reload-server initRouter");
        app.before(ctx -> {
            ctx.res.addHeader("Server", "hot-reload-server");
            ctx.res.setContentType("application/json");
        });
        app.exception(Exception.class, (e, ctx) -> {
            log.error("server error", e);
            ctx.json(ResponseKit.fail().setMsg(e.getMessage()));
        });
        app.get("/ping", ctx -> {
            ctx.result("pong");
        });

        for (Router router : routers) {
            router.bind(app);
        }
    }

    private void destroy() {
        log.info("hot-reload-server destroy");
        app.stop();
    }

    @Override
    public void start() {
        init();
        initRouter();
        app.start(config.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread(this::destroy, "server-shutdown-hook"));
    }

}
