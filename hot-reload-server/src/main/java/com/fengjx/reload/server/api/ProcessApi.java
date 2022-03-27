package com.fengjx.reload.server.api;

import com.fengjx.reload.common.utils.ProcessUtils;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Map;

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
        Map<Long, String> process = ProcessUtils.listProcessByJps(false);
        ctx.json(ResponseKit.ok(process));
    }

}
