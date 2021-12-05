package com.fengjx.reload.watcher.worker;

/**
 * @author fengjianxin
 * @since 2021-12-05
 */
public class WorkerFactory {

    public static Worker createWorker() {
        return new LocalWorker();
    }


}
