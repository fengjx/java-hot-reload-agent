package com.fengjx.reload.watcher;

import com.fengjx.reload.common.utils.DigestUtils;
import com.fengjx.reload.common.utils.ThreadUtils;
import com.fengjx.reload.core.consts.FileExtension;
import com.fengjx.reload.watcher.worker.Worker;
import com.fengjx.reload.watcher.worker.WorkerFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author FengJianxin
 */
@Slf4j
public class Watcher extends FileAlterationListenerAdaptor implements Runnable {

    private boolean running = false;
    private final String[] watchPaths;
    private FileAlterationMonitor monitor;
    private final ConcurrentHashMap<String, String> oldVersion = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> fileCache = new ConcurrentHashMap<>();
    private final Worker worker;

    public Watcher() {
        Config config = Config.get();
        this.watchPaths = config.getWatchPaths();
        this.worker = WorkerFactory.createWorker();
    }

    private void loadOldVersion(String[] watchPaths) throws IOException, NoSuchAlgorithmException {
        for (String watchPath : watchPaths) {
            Collection<File> files = FileUtils.listFiles(new File(watchPath), new String[]{
                    FileExtension.CLASS_FILE_EXT,
                    FileExtension.JAVA_FILE_EXT
            }, true);
            for (File file : files) {
                String checksum = DigestUtils.checksum(file);
                oldVersion.put(file.getAbsolutePath(), checksum);
            }
        }
    }


    /**
     * 文件创建执行
     */
    @Override
    public void onFileCreate(File file) {
        log.info("create: {}", file.getAbsolutePath());
        addCache(file);
    }

    /**
     * 文件创建修改
     */
    @Override
    public void onFileChange(File file) {
        log.info("modify: {}", file.getAbsolutePath());
        addCache(file);
    }

    /**
     * 文件删除
     */
    @Override
    public void onFileDelete(File file) {
        log.info("delete: {}", file.getAbsolutePath());
        removeCache(file);
    }

    private void addCache(File file) {
        synchronized (this) {
            try {
                String absolutePath = file.getAbsolutePath();
                String checksum = DigestUtils.checksum(file);
                String oldChecksum = fileCache.get(absolutePath);
                if (checksum.equals(oldChecksum)) {
                    return;
                }
                fileCache.put(absolutePath, checksum);
                log.info("add watch file cache: {}", absolutePath);
            } catch (Exception e) {
                log.error("add watch file cache error", e);
            }
        }
    }

    private void removeCache(File file) {
        synchronized (this) {
            String absolutePath = file.getAbsolutePath();
            fileCache.remove(absolutePath);
            oldVersion.remove(absolutePath);
            log.info("remove watch file cache: {}", absolutePath);
        }
    }

    public void stop() {
        log.info("watcher stop");
        try {
            running = false;
            if (monitor != null) {
                monitor.stop();
            }
        } catch (Exception e) {
            log.error("Watcher stop error", e);
        }
    }

    /**
     * 注册监听器
     */
    private List<FileAlterationObserver> createObservers(String[] dirs) {
        List<FileAlterationObserver> observers = Lists.newArrayList();
        for (String dir : dirs) {
            // 过滤器，只监听class文件
            IOFileFilter directories = FileFilterUtils.and(
                    FileFilterUtils.directoryFileFilter(),
                    HiddenFileFilter.VISIBLE);

            IOFileFilter classFiles = FileFilterUtils.and(
                    FileFilterUtils.fileFileFilter(),
                    FileFilterUtils.suffixFileFilter(FileExtension.CLASS_FILE_EXTENSION));

            IOFileFilter srcFiles = FileFilterUtils.and(
                    FileFilterUtils.fileFileFilter(),
                    FileFilterUtils.suffixFileFilter(FileExtension.JAVA_FILE_EXTENSION));

            IOFileFilter filter = FileFilterUtils.or(directories, classFiles, srcFiles);
            FileAlterationObserver observer = new FileAlterationObserver(new File(dir), filter);
            observer.addListener(this);
            observers.add(observer);
        }
        return observers;
    }

    private void batchReloadClass() {
        Set<String> fileSet = Sets.newHashSet();
        for (Map.Entry<String, String> entry : fileCache.entrySet()) {
            String path = entry.getKey();
            String checksum = entry.getValue();
            if (check(path, checksum)) {
                fileSet.add(path);
                // replace old version
                oldVersion.put(path, checksum);
            }
        }
        worker.doReload(fileSet);
    }

    private boolean check(String path, String checksum) {
        String oldChecksum = oldVersion.get(path);
        return !checksum.equals(oldChecksum);
    }

    @Override
    public void run() {
        log.info("watcher start");
        try {
            running = true;
            loadOldVersion(watchPaths);
            // 轮询间隔 3 秒
            long interval = TimeUnit.SECONDS.toMillis(3);
            monitor = new FileAlterationMonitor(interval, createObservers(watchPaths));
            // 开始监控
            monitor.start();
            ThreadUtils.run("worker-thread", true, () -> {
                log.info("worker start");
                while (running) {
                    batchReloadClass();
                    ThreadUtils.sleep(10 * 1000);
                }
            });
        } catch (Exception e) {
            log.error("watcher start error", e);
        }
    }
}
