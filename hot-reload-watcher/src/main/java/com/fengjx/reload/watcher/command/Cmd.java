package com.fengjx.reload.watcher.command;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * @author fengjianxin
 */
public interface Cmd {

    /**
     * 匹配命令
     */
    String[] name();

    /**
     * 帮助说明
     */
    String help();

    String lineSyntax();

    /**
     * 参数定义
     */
    void options(Options options);

    /**
     * 命令处理函数
     */
    void handle(CommandLine line);

}
