package com.fengjx.reload.watcher;

import com.fengjx.reload.common.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * @author fengjianxin
 */
@Slf4j
public class WatcherBootstrap {

    /**
     * invoke for agent
     */
    public static void boot() {
        Watcher watcher = new Watcher();
        ThreadUtils.run("watcher-thread", watcher);
        //noinspection AlibabaAvoidManuallyCreateThread
        Thread shutdown = new Thread("watcher-shutdown-hooker") {
            @Override
            public void run() {
                watcher.stop();
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
        Config.init(pid, path.split(","));
        log.info("watcher start, watchPath: {}, pid: {}", path, pid);
        boot();
    }

}
