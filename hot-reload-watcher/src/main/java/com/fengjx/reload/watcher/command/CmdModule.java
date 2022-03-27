package com.fengjx.reload.watcher.command;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * @author FengJianxin
 * @since 2022/3/13
 */
public class CmdModule extends AbstractModule {


    @Override
    protected void configure() {
        Multibinder<Cmd> multibinder = Multibinder.newSetBinder(binder(), Cmd.class);
        multibinder.addBinding().toInstance(new HelpCmd());
        multibinder.addBinding().toInstance(new ExitCmd());
        multibinder.addBinding().toInstance(new ReloadCmd());
        multibinder.addBinding().toInstance(new JpsCmd());
        multibinder.addBinding().toInstance(new SetPidCmd());
        multibinder.addBinding().toInstance(new ConfigCmd());
    }


}
