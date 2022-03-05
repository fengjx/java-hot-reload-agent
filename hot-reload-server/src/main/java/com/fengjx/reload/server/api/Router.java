package com.fengjx.reload.server.api;

import io.javalin.Javalin;

/**
 * @author FengJianxin
 * @since 2022/3/5
 */
public interface Router {

    /**
     * 绑定 api
     */
    void bind(Javalin app);


}
