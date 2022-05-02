package com.fengjx.reload.watcher.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author fengjianxin
 */
@Singleton
public class CmdFactory {


    private final Map<String, Cmd> cmdMap = new LinkedHashMap<>();

    @Inject
    public CmdFactory(Set<Cmd> cmds) {
        for (Cmd cmd : cmds) {
            for (String key : cmd.name()) {
                cmdMap.put(key, cmd);
            }
        }
    }


    public Cmd getCmd(String key) {
        return cmdMap.get(key);
    }


    public Set<Cmd> getAllCmd() {
        return new LinkedHashSet<>(cmdMap.values());
    }

}
