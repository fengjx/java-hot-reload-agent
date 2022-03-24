package com.fengjx.reload.server.api;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * @author FengJianxin
 * @since 2022/3/6
 */
public class ProcessApi implements Router {

    @Override
    public void bind(Javalin app) {
        app.get("/process/list", this::processList);
    }

    public void processList(Context ctx) {
        ctx.json(ResponseKit.ok());
    }

}
