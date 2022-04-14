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
public class ConfigCmd implements Cmd {

    @Inject
    private Config config;

    @Override
    public String[] key() {
        return new String[]{"config"};
    }

    @Override
    public String help() {
        return "查看当前配置";
    }

    @Override
    public void handle(String args) {
        AnsiLog.info("current config config" +
                "\n{}", JsonUtils.toPrettyJson(config));
    }

}
