package com.fengjx.reload.watcher.command;

import com.fengjx.reload.common.AnsiLog;
import com.fengjx.reload.common.utils.MapUtils;
import com.fengjx.reload.watcher.worker.WorkerFactory;
import com.google.inject.Inject;
import org.apache.commons.cli.CommandLine;

import java.util.Map;

/**
 * 加载变更 Class
 *
 * @author fengjianxin
 */
public class JpsCmd extends SampleCmd implements Cmd {

    @Inject
    private WorkerFactory workerFactory;

    @Override
    public String[] name() {
        return new String[]{"jps"};
    }

    @Override
    public String help() {
        return "查看 jvm 进程列表";
    }

    @Override
    public void handle(CommandLine line) {
        Map<Long, String> processMap = workerFactory.getWorker().jps();
        if (MapUtils.isEmpty(processMap)) {
            AnsiLog.info("jvm process is empty");
            return;
        }
        for (Map.Entry<Long, String> entry : processMap.entrySet()) {
            AnsiLog.info("{} {}", entry.getKey(), entry.getValue());
        }
    }

}
