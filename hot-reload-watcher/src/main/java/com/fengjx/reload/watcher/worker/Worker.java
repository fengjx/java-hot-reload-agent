package com.fengjx.reload.watcher.worker;

import java.util.Set;

/**
 * @author fengjianxin
 */
public interface Worker {


    void doReload(Set<String> files);


}
