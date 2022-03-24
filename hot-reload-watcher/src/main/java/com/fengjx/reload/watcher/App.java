package com.fengjx.reload.watcher;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.watcher.command.CommandListener;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author fengjianxin
 */
@Singleton
public class App {

    private boolean running = false;
    @Inject
    private Watcher watcher;
    @Inject
    private CommandListener commandListener;

    public void reload() {
        if (watcher == null) {
            AnsiLog.warn("watcher not start");
            return;
        }
        watcher.reloadClass();
    }

    public void start() {
        running = true;
        startWatcher();
        startCommandListener();
    }

    private void startWatcher() {
        watcher.start();
    }

    private void startCommandListener() {
        commandListener.listen();
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        watcher.stop();
        System.exit(0);
    }

    public boolean isRunning() {
        return running;
    }


}
