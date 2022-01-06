package com.fengjx.reload.common.jvm;

import com.fengjx.reload.common.consts.FileExtension;
import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * @author fengjianxin
 */
@UtilityClass
public class ClassUtils {

    /**
     * 文件路径转类名
     *
     * @param basePath      根路径
     * @param classFilePath 文件物理路径
     * @return q全路径类名
     */
    public static String fileToClassName(String basePath, String classFilePath) {
        String path = classFilePath
                .substring(basePath.length())
                .replace(FileExtension.CLASS_FILE_EXTENSION, "")
                .replace(FileExtension.JAVA_FILE_EXTENSION, "");

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

    public static String classNameToPath(String basePath, String className, boolean isClass) {
        String extension = isClass ? FileExtension.CLASS_FILE_EXTENSION : FileExtension.JAVA_FILE_EXTENSION;
        if (basePath.endsWith(File.separator)) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        String classNamePath = className.replaceAll("\\.", File.separator);
        return basePath + File.separator + classNamePath + extension;
    }


}
