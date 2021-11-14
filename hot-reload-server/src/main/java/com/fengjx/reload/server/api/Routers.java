package com.fengjx.reload.server.api;

import spark.Route;

/**
 * @author fengjianxin
 */
public class Routers {

    public static Route hotfix() {
        return (request, response) -> {
            String className = request.params("className");
            return "ok";
        };
    }


}
