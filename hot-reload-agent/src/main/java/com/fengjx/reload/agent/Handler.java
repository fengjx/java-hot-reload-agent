package com.fengjx.reload.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author FengJianxin
 * @since 2021-11-20
 */
public interface Handler {

    void process(String args, Instrumentation inst);

}
