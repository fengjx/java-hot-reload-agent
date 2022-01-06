package com.fengjx.reload.watcher.config;

import com.fengjx.reload.common.utils.JsonUtils;
import com.fengjx.reload.common.utils.PropUtils;
import com.fengjx.reload.common.utils.StrUtils;
import lombok.Data;

import java.util.function.Consumer;

/**
 * @author fengjianxin
 */
@Data
public class Config {

    private String mode;
    private String[] watchPaths;
    private LocalConfig local;

    private ServerConfig server;

    private static Config INSTANCE = new Config();

    public static void init(String mode, String json) {
        if (StrUtils.isNotBlank(json)) {
            INSTANCE = JsonUtils.fromJson(json, Config.class);
        }
        INSTANCE.setMode(mode);
        // overwrite
        setByProp("local_pid", item -> {
            INSTANCE.getLocal().setPid(Integer.parseInt(item));
        });
        setByProp("local_watch_path", item -> {
            INSTANCE.setWatchPaths(StrUtils.trims(item.split(",")));
        });
        setByProp("server_pid", item -> {
            INSTANCE.getServer().setPid(Integer.parseInt(item));
        });
        setByProp("server_host", item -> {
            INSTANCE.getServer().setHost(item);
        });
    }

    private static void setByProp(String key, Consumer<String> consumer) {
        String value = PropUtils.getProp(key);
        if (value != null) {
            consumer.accept(value);
        }
    }


    public static Config me() {
        return INSTANCE;
    }

}
