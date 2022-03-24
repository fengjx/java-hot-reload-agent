package com.fengjx.reload.watcher;

import com.fengjx.reload.common.AnsiLog;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.cli.*;

/**
 * @author fengjianxin
 */
public class WatcherBootstrap {


    public static void main(String[] args) throws Exception {
        CommandLineParser defaultParser = new DefaultParser();
        Options options = new Options();
        options.addOption("c", "config", true, "start mode");

        CommandLine commandLine = defaultParser.parse(options, args);
        String config = commandLine.getOptionValue("c");
        final Injector injector = Guice.createInjector(new AppModule(config));
        final App app = injector.getInstance(App.class);
        app.start();
        AnsiLog.info("start hot-reload-watcher-boot");
    }

}
