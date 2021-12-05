package com.fengjx.reload.common.utils;

/**
 * @author fengjianxin
 */
public class ThreadUtils {

    public static Thread run(String name, Runnable runnable) {
        return run(name, false, runnable);
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static Thread run(String name, boolean daemon, Runnable runnable) {
        Thread thread = new Thread(runnable, name);
        thread.setDaemon(daemon);
        thread.start();
        return thread;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
