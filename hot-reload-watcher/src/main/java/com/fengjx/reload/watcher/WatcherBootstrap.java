package com.fengjx.reload.watcher;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.watcher.config.Config;
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
                .addOption("m", "mode", false, "start mode")
                .addOption("c", "config", false, "start mode");

        CommandLine commandLine = defaultParser.parse(options, args);
        String mode = commandLine.getOptionValue("mode");
        String config = commandLine.getOptionValue("config");
        Config.init(mode, config);
        AnsiLog.info("start hot-reload-watcher-boot");
        App.me().start();
    }

}
