package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.StrUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.commons.cli.CommandLine;

/**
 * 退出程序
 *
 * @author fengjianxin
 */
public class HelpCmd extends SampleCmd implements Cmd {

    @Inject
    private Injector injector;

    @Override
    public String[] name() {
        return new String[]{"h", "help", "?"};
    }

    @Override
    public String help() {
        return "使用帮助";
    }

    @Override
    public void handle(CommandLine line) {
        CmdFactory cmdFactory = injector.getInstance(CmdFactory.class);
        StringBuilder helpInfo = new StringBuilder("usage \n");
        cmdFactory.getAllCmd().forEach(item -> {
            helpInfo.append(StrUtils.join(item.name(), ",")).append(" : ").append(item.help()).append("\n");
        });
        AnsiLog.info(helpInfo.toString());
    }

}
