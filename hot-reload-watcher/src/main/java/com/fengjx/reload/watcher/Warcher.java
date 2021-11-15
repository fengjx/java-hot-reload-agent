package com.fengjx.reload.watcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;

/**
 * @author FengJianxin
 */
@Slf4j
@AllArgsConstructor
public class Warcher {

    private Instrumentation instrumentation;

    public void bind(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public void start() {
        log.info("watcher start");
    }

}
