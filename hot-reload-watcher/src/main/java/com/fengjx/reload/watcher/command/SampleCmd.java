package com.fengjx.reload.watcher.command;

import org.apache.commons.cli.Options;

/**
 * @author FengJianxin
 * @since 2022/5/1
 */
public abstract class SampleCmd implements Cmd {

    @Override
    public void options(Options options) {
    }

    @Override
    public String lineSyntax() {
        return null;
    }

}
