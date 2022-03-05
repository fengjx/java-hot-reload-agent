package com.fengjx.reload.server.api;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * @author FengJianxin
 * @since 2022/3/5
 */
public class ApiModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Router> uriBinder = Multibinder.newSetBinder(binder(), Router.class);
        uriBinder.addBinding().to(HotReloadApi.class);
    }

}
