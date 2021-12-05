package com.fengjx.reload.watcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WatcherTest {


    public static void main(String[] args) throws InterruptedException, IOException {
        Path temp = Files.createTempDirectory("watcherTest");
        Config.init("", new String[]{
                temp.toFile().getAbsolutePath()
        });
        new Thread(new Watcher()).start();
        Thread.sleep(1000 * 60 * 10);
    }


}
