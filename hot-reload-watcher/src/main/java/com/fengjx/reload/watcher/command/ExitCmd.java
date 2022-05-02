package com.fengjx.reload.watcher.command;

import com.fengjx.reload.watcher.App;
import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;

/**
 * 退出程序
 *
 * @author fengjianxin
 */
public class ExitCmd extends SampleCmd implements Cmd {

    @Inject
    private App app;

    @Override
    public String[] name() {
        return new String[]{"exit", "q"};
    }

    @Override
    public String help() {
        return "退出进程";
    }

    @Override
    public void handle(CommandLine line) {
        app.stop();
    }

}
