package com.fengjx.reload.watcher.worker;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author FengJianxin
 * @since 2022/3/13
 */
public class WorkerModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(Worker.class).annotatedWith(Names.named("localWorker")).to(LocalWorker.class);
        bind(Worker.class).annotatedWith(Names.named("remoteWorker")).to(RemoteWorker.class);
    }


}
