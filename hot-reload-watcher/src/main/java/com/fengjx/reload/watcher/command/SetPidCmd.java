package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.JsonUtils;
import com.fengjx.reload.watcher.config.Config;
import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;

import java.util.List;

/**
 * 加载变更 Class
 *
 * @author fengjianxin
 */
public class SetPidCmd extends SampleCmd implements Cmd {

    @Inject
    private Config config;

    @Override
    public String[] name() {
        return new String[]{"set-pid"};
    }

    @Override
    public String help() {
        return "修改监听 jvm 进程 ID";
    }

    @Override
    public String lineSyntax() {
        return "set-pid <pid>";
    }

    @Override
    public void handle(CommandLine line) {
        List<String> argList = line.getArgList();
        if (argList.size() < 1) {
            AnsiLog.warn("请输入 jvm 进程 id，通过 jps 指令可查看进程 id 列表, eg: set-pid 1000");
            return;
        }
        try {
            Integer.parseInt(argList.get(0));
        } catch (NumberFormatException e) {
            AnsiLog.info("pid parse error [{}]", argList.get(0));
            return;
        }
        config.setPid(Integer.parseInt(argList.get(0)));
        AnsiLog.info("current config config" +
                "\n{}", JsonUtils.toPrettyJson(config));
    }

}
