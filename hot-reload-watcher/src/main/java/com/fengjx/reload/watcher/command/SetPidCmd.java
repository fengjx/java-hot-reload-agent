package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.JsonUtils;
import com.fengjx.reload.watcher.config.Config;
import com.google.inject.Inject;

/**
 * 加载变更 Class
 *
 * @author fengjianxin
 */
public class SetPidCmd implements Cmd {

    @Inject
    private Config config;

    @Override
    public String[] key() {
        return new String[]{"set-pid"};
    }

    @Override
    public String help() {
        return "修改进程 ID";
    }

    @Override
    public void handle(String args) {
        String[] arr = args.split("\\s+");
        if (arr.length < 2) {
            AnsiLog.warn("please input pid arg, eg: [ set-pid 1000 ]");
            return;
        }
        try {
            Integer.parseInt(arr[1]);
        } catch (NumberFormatException e) {
            AnsiLog.info("pid parse error [{}]", arr[1]);
            return;
        }
        config.setPid(Integer.parseInt(arr[1]));
        AnsiLog.info("current config config" +
                "\n{}", JsonUtils.toPrettyJson(config));
    }

}
