package com.fengjx.reload.watcher;

public class WatcherTest {


    public static void main(String[] args) throws InterruptedException {
        new Thread(new Watcher("","/Users/fengjianxin/workspaces/git/my-github/java-hot-reload-agent/example/target/classes")).start();
        Thread.sleep(1000 * 60 * 10);
    }


}
