package com.fengjx.reload.agent;

import com.fengjx.reload.common.AgentConfig;
import com.fengjx.reload.common.AnsiLog;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @author FengJianxin
 */
public class ReloadClassHandler implements Handler {

    private static final String HOT_RELOAD_WORKER_CLASS = "com.fengjx.reload.core.HotReloadWorker";
    private static final String HOT_RELOAD_RELOAD_METHOD = "doReload";

    private static volatile ClassLoader myClassLoader;

    @Override
    public void process(String args, Instrumentation inst) {
        if (args == null) {
            throw new IllegalArgumentException("Agent args is null");
        }
        try {
            final String replaceFilePath = args;
            File coreJarFile = AgentConfig.getCoreJarFile();
            if (myClassLoader == null) {
                myClassLoader = new MyClassloader(
                        new URL[]{coreJarFile.toURI().toURL()});
            }
            AnsiLog.info("agent core urls is {}", coreJarFile.toURI().toURL());
            Class<?> hotReloadWorkerClass = myClassLoader.loadClass(HOT_RELOAD_WORKER_CLASS);
            Method method = hotReloadWorkerClass.getDeclaredMethod(HOT_RELOAD_RELOAD_METHOD, Instrumentation.class, String.class);
            method.invoke(null, inst, replaceFilePath);
        } catch (Exception e) {
            AnsiLog.error("Reload failed: {}", e.getMessage());
            AnsiLog.error(e);
        }
    }
}
