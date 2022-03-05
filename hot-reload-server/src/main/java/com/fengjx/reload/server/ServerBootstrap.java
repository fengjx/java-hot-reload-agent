package com.fengjx.reload.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

/**
 * @author fengjianxin
 */
public class ServerBootstrap {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/static", Location.CLASSPATH);
        });
        final Injector injector = Guice.createInjector(new AppModule(app));
        final Server server = injector.getInstance(Server.class);
        server.start();
    }


}
