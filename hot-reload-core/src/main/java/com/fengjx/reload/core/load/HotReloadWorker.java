package com.fengjx.reload.core.load;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuzhengyang
 * 2021/1/4
 */
@Slf4j
public class HotReloadWorker {

    private static final Map<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    public static void doReload(Instrumentation instrumentation, String className, String replaceTargetFile) {
        log.info("Start reloading. Current classloader is " + HotReloadWorker.class.getClassLoader());
        try {
            if (replaceTargetFile == null) {
                log.error("Invalid argument file is null");
                return;
            }
            File file = Paths.get(replaceTargetFile).toFile();
            if (replaceTargetFile.endsWith(".class")) {
                log.info("Reload by class file");
                byte[] newClazzByteCode = Files.readAllBytes(file.toPath());
                doReloadClassFile(instrumentation, className, newClazzByteCode);
            }
        } catch (Exception e) {
            log.error("Reload failed {} {}", className, replaceTargetFile, e);
        }
    }

    private static void doReloadClassFile(Instrumentation instrumentation, String className,
                                          byte[] newClazzByteCode) throws UnmodifiableClassException, ClassNotFoundException {
        Class<?> clazz = getToReloadClass(instrumentation, className, newClazzByteCode);
        if (clazz == null) {
            log.error("Class " + className + " not found");
        } else {
            instrumentation.redefineClasses(new ClassDefinition(clazz, newClazzByteCode));
            log.info("Congratulations! Reload " + clazz + " success!");
        }
    }

    private static Class<?> getToReloadClass(Instrumentation instrumentation, String className,
                                             byte[] newClazzByteCode) {
        Class<?> clazz = findTargetClass(className, instrumentation);
        if (clazz == null) {
            clazz = defineNewClass(className, newClazzByteCode, clazz);
        }
        return clazz;
    }

    private static Class<?> defineNewClass(String className, byte[] newClazzByteCode, Class<?> clazz) {
        log.info("Class " + className + " not found, try to define a new class");
        ClassLoader classLoader = HotReloadWorker.class.getClassLoader();
        try {
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class,
                    byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            clazz = (Class<?>) defineClass.invoke(classLoader, className, newClazzByteCode
                    , 0, newClazzByteCode.length);
            log.info("Class " + className + " define success " + clazz);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("defineNewClass {} failed ", className, e);
        }
        return clazz;
    }

    @SuppressWarnings("rawtypes")
    private static Class<?> findTargetClass(String className, Instrumentation instrumentation) {
        return CLASS_CACHE.computeIfAbsent(className, clazzName -> {
            Class[] allLoadedClasses = instrumentation.getAllLoadedClasses();
            return Arrays.stream(allLoadedClasses)
                    .parallel()
                    .filter(clazz -> clazzName.equals(clazz.getName()))
                    .findFirst()
                    .orElse(null);
        });
    }
}
