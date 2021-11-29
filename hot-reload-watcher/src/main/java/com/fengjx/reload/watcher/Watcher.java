package com.fengjx.reload.watcher;

import com.fengjx.reload.common.Config;
import com.fengjx.reload.common.utils.StrUtils;
import com.fengjx.reload.core.consts.FileExtension;
import com.sun.tools.attach.VirtualMachine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author FengJianxin
 */
@Slf4j
public class Watcher extends FileAlterationListenerAdaptor implements Runnable {


    private final String pid;
    private final String watchPath;
    private final WatchService watchService;

    public Watcher(String pid, String watchPath) {
        this.pid = pid;
        this.watchPath = watchPath;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get(watchPath).register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件创建执行
     */
    @Override
    public void onFileCreate(File file) {
        log.info("[create]:" + file.getAbsolutePath());
        reloadClass(fileToClassName(file), file.getAbsolutePath());
    }

    /**
     * 文件创建修改
     */
    @Override
    public void onFileChange(File file) {
        log.info("[modify]:" + file.getAbsolutePath());
        reloadClass(fileToClassName(file), file.getAbsolutePath());
    }

    /**
     * 文件删除
     */
    @Override
    public void onFileDelete(File file) {
        log.info("[delete]:" + file.getAbsolutePath());
        // todo
    }

    public void stop() {
        log.info("watcher stop");
        try {
            watchService.close();
        } catch (IOException e) {
            log.error("watcher stop error", e);
        }
    }

    private String fileToClassName(File classFile) {
        String path = classFile.getAbsolutePath().replace(watchPath, "")
                .replace(FileExtension.CLASS_FILE_EXTENSION, "");
        String className = path.replaceAll("/", ".")
                .replaceAll("\\\\", ".");
        if (className.startsWith(".")) {
            className = className.substring(1);
        }
        if (className.endsWith(".")) {
            className = className.substring(0, className.length() - 1);
        }
        return className;
    }

    private synchronized void reloadClass(String className, String classFilePath) {
        log.info("reload class: {}, {}", className, classFilePath);
        if (StrUtils.isBlank(pid)) {
            return;
        }
        VirtualMachine attach = null;
        try {
            attach = VirtualMachine.attach(pid);
            log.debug("load agent jar: {}", Config.getAgentJar());
            attach.loadAgent(Config.getAgentJar(), className + "," + classFilePath);
        } catch (Exception e) {
            log.error("reload class error", e);
        } finally {
            if (attach != null) {
                try {
                    attach.detach();
                } catch (IOException e) {
                    log.error("vm detach error", e);
                }
            }
            log.info("reload class finished");
        }
    }

    @Override
    public void run() {
        log.info("watcher start");
        // 轮询间隔 3 秒
        long interval = TimeUnit.SECONDS.toMillis(3);
        // 过滤器，只监听class文件
        IOFileFilter directories = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(FileExtension.CLASS_FILE_EXTENSION),
                FileFilterUtils.suffixFileFilter(FileExtension.JAVA_FILE_EXTENSION));
        IOFileFilter filter = FileFilterUtils.or(directories, files);
        FileAlterationObserver observer = new FileAlterationObserver(new File(watchPath), filter);
        observer.addListener(this);
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        try {
            monitor.start();
        } catch (Exception e) {
            log.error("watcher start error", e);
        }
    }
}
