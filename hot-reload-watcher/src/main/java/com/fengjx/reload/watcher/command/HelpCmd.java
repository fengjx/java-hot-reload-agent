package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.StrUtils;

/**
 * 退出程序
 *
 * @author fengjianxin
 */
public class HelpCmd implements Cmd {

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
        CmdFactory.getAllCmd().forEach(item -> {
            AnsiLog.info("{} : {}", StrUtils.join(item.key(), ","), item.help());
        });
    }

}
