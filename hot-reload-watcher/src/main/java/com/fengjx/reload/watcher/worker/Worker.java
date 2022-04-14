package com.fengjx.reload.watcher.worker;

import java.util.Map;
import java.util.Set;

/**
 * @author fengjianxin
 */
public interface Worker {

    /**
     * 查看 jvm 进程列表
     */
    Map<Long, String> jps();

    void doReload(Set<String> files);


}
