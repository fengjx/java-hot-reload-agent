package com.fengjx.reload.watcher.command;


/**
 * @author fengjianxin
 */
public interface Cmd {

    String[] key();

    String help();

    void handle();

}
