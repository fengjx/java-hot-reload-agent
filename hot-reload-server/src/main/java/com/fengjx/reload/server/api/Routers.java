package com.fengjx.reload.server.api;

import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

/**
 * @author fengjianxin
 */
@Slf4j
public class Routers {

    public static Object hotfix(Request req, Response resp) {
        String className = req.params("className");
        log.info("className: {}", className);
        return "ok";
    }

}
