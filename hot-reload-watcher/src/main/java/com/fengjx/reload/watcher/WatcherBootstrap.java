package com.fengjx.reload.watcher;

import com.fengjx.reload.common.AnsiLog;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * @author fengjianxin
 */
public class WatcherBootstrap {


    public static void main(String[] args) throws Exception {
        CommandLineParser defaultParser = new DefaultParser();
        Options options = new Options()
                .addOption("path", "path", true, "hot reload class watch path")
                .addOption("pid", "pid", true, "java process id");
        CommandLine commandLine = defaultParser.parse(options, args);
        String path = commandLine.getOptionValue("path");
        String pid = commandLine.getOptionValue("pid");
        Config.init(pid, path.split(","));
        AnsiLog.info("start hot-reload-watcher-boot");
        AnsiLog.info("pid: {}", pid);
        AnsiLog.info("watchPath: {}", path);
        App.me().start();
    }

}
