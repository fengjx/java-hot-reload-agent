package com.fengjx.reload.watcher;

import com.fengjx.reload.watcher.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WatcherTest {


    public static void main(String[] args) throws InterruptedException, IOException {
        Path temp = Files.createTempDirectory("watcherTest");
        Config.init("local", "");
        new Watcher().start();
        Thread.sleep(1000 * 60 * 10);
    }


}
