package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.watcher.App;
import com.google.inject.Inject;

/**
 * 退出程序
 *
 * @author fengjianxin
 */
public class ExitCmd implements Cmd {

    @Inject
    private App app;

    @Override
    public String[] key() {
        return new String[]{"exit", "q"};
    }

    @Override
    public String help() {
        return "退出进程";
    }

    @Override
    public void handle() {
        AnsiLog.info("stop hot-reload-watcher-boot");
        app.stop();
    }

}
