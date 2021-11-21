package com.fengjx.reload.watcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fengjianxin
 */
@Slf4j
public class WatcherBootstrap {

    @SuppressWarnings("AlibabaThreadPoolCreation")
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static WatcherBootstrap watcherBootstrap;
    private Thread shutdown;
    private String pid;
    private final String watchPath;
    private Watcher watcher;

    public WatcherBootstrap(String pid, String watchPath) {
        this.pid = pid;
        this.watchPath = watchPath;
    }

    public void destroy() {
        EXECUTOR.shutdown();
    }

    /**
     * invoke for agent
     */
    public synchronized static WatcherBootstrap getInstance(String pid, String watchPath) {
        if (watcherBootstrap == null) {
            watcherBootstrap = new WatcherBootstrap(pid, watchPath);
        }
        return watcherBootstrap;
    }

    /**
     * invoke for agent
     */
    public void boot() {
        watcher = new Watcher(pid, watchPath);
        EXECUTOR.submit(watcher);
        //noinspection AlibabaAvoidManuallyCreateThread
        shutdown = new Thread("watcher-shutdown-hooker") {
            @Override
            public void run() {
                WatcherBootstrap.this.destroy();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdown);
    }

    public static void main(String[] args) throws Exception {
        CommandLineParser defaultParser = new DefaultParser();
        Options options = new Options()
                .addOption("path", "path", true, "hot reload class watch path")
                .addOption("pid", "pid", true, "java process id");
        CommandLine commandLine = defaultParser.parse(options, args);
        String path = commandLine.getOptionValue("path");
        String pid = commandLine.getOptionValue("pid");
        watcherBootstrap = getInstance(pid, path);
        log.info("watcher start, watchPath: {}, pid: {}", path, pid);
        watcherBootstrap.boot();
    }

}
