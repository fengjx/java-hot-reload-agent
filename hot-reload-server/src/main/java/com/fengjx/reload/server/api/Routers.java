package com.fengjx.reload.server.api;

import lombok.extern.slf4j.Slf4j;
import spark.Route;

/**
 * @author fengjianxin
 */
@Slf4j
public class Routers {

    public static Route hotfix() {
        return (request, response) -> {
            String className = request.params("className");
            log.info("className: {}", className);
            return "ok";
        };
    }


}
