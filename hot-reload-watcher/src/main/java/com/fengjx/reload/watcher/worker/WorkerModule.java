package com.fengjx.reload.watcher.worker;

import com.fengjx.reload.watcher.consts.WorkerConst;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * @author FengJianxin
 * @since 2022/3/13
 */
public class WorkerModule extends AbstractModule {


    @Override
    protected void configure() {
        MapBinder<String, Worker> mapbinder = MapBinder.newMapBinder(binder(), String.class, Worker.class);
        mapbinder.addBinding(WorkerConst.localWorkerName).to(LocalWorker.class);
        mapbinder.addBinding(WorkerConst.remoteWorkerName).to(RemoteWorker.class);
    }


}
