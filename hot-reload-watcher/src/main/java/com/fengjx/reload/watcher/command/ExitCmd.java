package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.watcher.App;

/**
 * 退出程序
 *
 * @author fengjianxin
 */
public class ExitCmd implements Cmd {

    @Override
    public String[] key() {
        return new String[]{"exit"};
    }

    @Override
    public String help() {
        return "退出进程";
    }

    @Override
    public void handle() {
        AnsiLog.info("stop hot-reload-watcher-boot");
        App.me().stop();
    }

}
