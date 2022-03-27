package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.ThreadUtils;
import com.fengjx.reload.watcher.App;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.NoSuchElementException;
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
    private Thread thread;

    public void listen() {
        thread = ThreadUtils.run("command-listener", true, this);
    }

    @Override
    public void run() {
        doWork();
    }

    private void doWork() {
        while (app.isRunning()) {
            try {
                AnsiLog.info("输入指令，'h' 查看帮助");
                String line = new Scanner(System.in).nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] arr = line.split("\\s+");
                Cmd cmd = cmdFactory.getCmd(arr[0]);
                if (cmd != null) {
                    cmd.handle(line);
                }
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                AnsiLog.error("cmd exec error: {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            if (thread.isInterrupted()) {
                return;
            }
            thread.interrupt();
            thread.join(3000);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        AnsiLog.info("commandListener stop");
    }

}
