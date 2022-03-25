package com.fengjx.reload.watcher.worker;

import com.fengjx.reload.watcher.config.Config;
import com.fengjx.reload.watcher.consts.WorkerConst;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Map;

/**
 * @author fengjianxin
 * @since 2021-12-05
 */
@Singleton
public class WorkerFactory {

    @Inject
    private Config config;
    @Inject
    private Map<String, Worker> workerMap;

    public Worker getWorker() {
        if (config.isLocalMode()) {
            return workerMap.get(WorkerConst.localWorkerName);
        }
        return workerMap.get(WorkerConst.remoteWorkerName);
    }


}
