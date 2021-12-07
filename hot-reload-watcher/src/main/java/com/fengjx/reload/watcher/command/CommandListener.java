package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.ThreadUtils;
import com.fengjx.reload.watcher.App;

import java.util.Scanner;

/**
 * @author fengjianxin
 */
public class CommandListener implements Runnable {


    private final App app = App.me();

    public void listen() {
        ThreadUtils.run("command-listener", true, this);
    }

    @Override
    public void run() {
        while (app.isRunning()) {
            AnsiLog.info("input command");
            String line = new Scanner(System.in).nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] arr = line.split("\\s+");
            Cmd cmd = CmdFactory.getCmd(arr[0]);
            if (cmd != null) {
                cmd.handle();
            }
        }
        AnsiLog.info("CommandListener stop");
    }
}
