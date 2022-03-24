package com.fengjx.reload.watcher;

import com.fengjx.reload.watcher.command.CmdModule;
import com.fengjx.reload.watcher.config.Config;
import com.fengjx.reload.watcher.worker.WorkerModule;
import com.google.inject.AbstractModule;

import java.io.IOException;

/**
 * @author FengJianxin
 * @since 2022/3/13
 */
public class AppModule extends AbstractModule {

    private final Config config;

    public AppModule(String configPath) {
        try {
            config = Config.newInstance(configPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void configure() {
        bind(Config.class).toInstance(config);
        install(new WorkerModule());
        install(new CmdModule());
    }


}
