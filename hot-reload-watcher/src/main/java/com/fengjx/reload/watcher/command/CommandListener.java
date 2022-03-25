package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.ThreadUtils;
import com.fengjx.reload.watcher.App;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Scanner;

/**
 * @author fengjianxin
 */
@Singleton
public class CommandListener implements Runnable {

    @Inject
    private App app;
    @Inject
    private CmdFactory cmdFactory;

    public void listen() {
        ThreadUtils.run("command-listener", true, this);
    }

    @Override
    public void run() {
        while (app.isRunning()) {
            AnsiLog.info("输入指令，'h' 查看帮助");
            String line = new Scanner(System.in).nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] arr = line.split("\\s+");
            Cmd cmd = cmdFactory.getCmd(arr[0]);
            if (cmd != null) {
                cmd.handle();
            }
        }
        AnsiLog.info("CommandListener stop");
    }
}
