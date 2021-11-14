package com.fengjx.reload.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import java.net.URL;

/**
 * agent 启动类
 *
 * @author FengJianxin
 * @since 2021-11-12
 */
public class AgentBootstrap {

    private static PrintStream ps = System.err;

    static {
        try {
            File agentLogDir = new File(System.getProperty("user.dir") + File.separator
                    + "logs" + File.separator);
            if (!agentLogDir.exists()) {
                agentLogDir.mkdirs();
            }
            if (!agentLogDir.exists()) {
                agentLogDir = new File(System.getProperty("java.io.tmpdir") + File.separator
                        + "logs" + File.separator);
                if (!agentLogDir.exists()) {
                    agentLogDir.mkdirs();
                }
            }
            File log = new File(agentLogDir, "agent.log");
            if (!log.exists()) {
                log.createNewFile();
            }
            System.out.println("agent log path: " + log.getAbsolutePath());
            ps = new PrintStream(new FileOutputStream(log, true));
        } catch (Throwable t) {
            t.printStackTrace(ps);
        }
    }

    /**
     * <pre>
     * 1. 全局持有classloader用于隔离 Arthas 实现，防止多次attach重复初始化
     * 2. ClassLoader在arthas停止时会被reset
     * 3. 如果ClassLoader一直没变，则 com.taobao.arthas.core.server.ArthasBootstrap#getInstance 返回结果一直是一样的
     * </pre>
     */
    private static volatile ClassLoader myClassLoader;

    public static void premain(String args, Instrumentation inst) {
        main(args, inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(args, inst);
    }

    /**
     * 让下次再次启动时有机会重新加载
     */
    public static void resetArthasClassLoader() {
        myClassLoader = null;
    }

    private static ClassLoader getClassLoader(Instrumentation inst, File arthasCoreJarFile) throws Throwable {
        // 构造自定义的类加载器
        return loadOrDefineClassLoader(arthasCoreJarFile);
    }

    private static ClassLoader loadOrDefineClassLoader(File arthasCoreJarFile) throws Throwable {
        if (myClassLoader == null) {
            myClassLoader = new MyClassloader(new URL[]{arthasCoreJarFile.toURI().toURL()});
        }
        return myClassLoader;
    }

    private static synchronized void main(String args, final Instrumentation inst) {
        try {
            System.out.println("agent start");
            ps.println("agent start");
        } catch (Throwable t) {
            t.printStackTrace(ps);
            try {
                if (ps != System.err) {
                    ps.close();
                }
            } catch (Throwable tt) {
                // ignore
            }
            throw new RuntimeException(t);
        }
    }

}
