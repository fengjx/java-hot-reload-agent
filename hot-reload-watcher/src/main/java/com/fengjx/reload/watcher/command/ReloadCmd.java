package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.watcher.App;
import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;

/**
 * 加载变更 Class
 *
 * @author fengjianxin
 */
public class ReloadCmd extends SampleCmd implements Cmd {

    @Inject
    private App app;

    @Override
    public String[] name() {
        return new String[]{"r"};
    }

    @Override
    public String help() {
        return "重新加载变更的 Class";
    }

    @Override
    public void handle(CommandLine line) {
        AnsiLog.info("do reload class");
        app.reload();
    }

}
