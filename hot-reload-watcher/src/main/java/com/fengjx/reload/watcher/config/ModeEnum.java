package com.fengjx.reload.watcher.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author FengJianxin
 * @since 2022/3/26
 */
@Getter
@AllArgsConstructor
public enum ModeEnum {

    LOCAL("local", "localWorker", "本地模式"),
    SERVER("server", "remoteWorker","远程模式");

    private String mode;
    private String workerName;
    private String desc;

    public static ModeEnum of(String mode) {
        return Arrays.stream(ModeEnum.values()).filter(item -> item.getMode().contentEquals(mode)).findFirst().orElse(null);
    }

}
