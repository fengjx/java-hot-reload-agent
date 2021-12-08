package com.fengjx.reload.watcher.command;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author fengjianxin
 */
public class CmdFactory {


    private static final Map<String, Cmd> CMD_MAP = new LinkedHashMap<>();

    static {
        create(new ReloadCmd(), new ExitCmd(), new HelpCmd());
    }

    private static void create(Cmd... cmds) {
        for (Cmd cmd : cmds) {
            for (String key : cmd.key()) {
                CMD_MAP.put(key, cmd);
            }
        }
    }

    public static Cmd getCmd(String key) {
        return CMD_MAP.get(key);
    }


    public static Set<Cmd> getAllCmd() {
        return new LinkedHashSet<>(CMD_MAP.values());
    }

}
