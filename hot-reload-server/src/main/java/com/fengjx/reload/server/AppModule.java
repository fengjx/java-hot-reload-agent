package com.fengjx.reload.server;

import com.fengjx.reload.server.api.ApiModule;
import com.google.inject.AbstractModule;
import io.javalin.Javalin;

/**
 * @author fengjianxin
 */
public class AppModule extends AbstractModule {

    private final Javalin app;

    public AppModule(Javalin app){
        this.app = app;
    }

    @Override
    protected void configure() {
        bind(Server.class).to(JavalinServer.class);
        bind(Javalin.class).toInstance(this.app);
        install(new ApiModule());
    }
}
