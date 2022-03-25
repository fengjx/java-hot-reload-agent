package com.fengjx.reload.watcher;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;

public class WatcherTest {


    public static void main(String[] args) throws InterruptedException, IOException {
        final Injector injector = Guice.createInjector(new AppModule(""));
        Watcher watcher = injector.getInstance(Watcher.class);
        watcher.start();
        Thread.sleep(1000 * 60 * 10);
    }


}
