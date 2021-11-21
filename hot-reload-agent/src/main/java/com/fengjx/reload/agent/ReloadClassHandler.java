package com.fengjx.reload.agent;

import com.fengjx.reload.common.Config;

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
        String[] splits = args.split(",");
        if (splits.length < 2) {
            throw new IllegalArgumentException(args);
        }
        try {
            File coreJarFile = Config.getCoreJarFile();
            if (myClassLoader == null) {
                myClassLoader = new MyClassloader(
                        new URL[]{coreJarFile.toURI().toURL()});
            }
            System.out.println("agent core urls is " + coreJarFile.toURI().toURL());
            Class<?> hotReloadWorkerClass = myClassLoader.loadClass(HOT_RELOAD_WORKER_CLASS);
            Method method = hotReloadWorkerClass.getDeclaredMethod(HOT_RELOAD_RELOAD_METHOD, Instrumentation.class, String[].class);
            method.invoke(null, inst, splits);
        } catch (Exception e) {
            System.out.printf("Reload failed %s%n", e);
        }
    }
}
