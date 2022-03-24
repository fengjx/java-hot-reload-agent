package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.StrUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * 退出程序
 *
 * @author fengjianxin
 */
public class HelpCmd implements Cmd {

    @Inject
    private Injector injector;

    @Override
    public String[] key() {
        return new String[]{"h", "help", "?"};
    }

    @Override
    public String help() {
        return "使用帮助";
    }

    @Override
    public void handle() {
        CmdFactory cmdFactory = injector.getInstance(CmdFactory.class);
        cmdFactory.getAllCmd().forEach(item -> {
            AnsiLog.info("{} : {}", StrUtils.join(item.key(), ","), item.help());
        });
    }

}
