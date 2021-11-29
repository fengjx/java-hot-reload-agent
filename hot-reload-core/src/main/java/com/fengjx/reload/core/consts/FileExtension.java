package com.fengjx.reload.core.consts;

import javax.tools.JavaFileObject;

public interface FileExtension {

    String CLASS_FILE_EXTENSION = JavaFileObject.Kind.CLASS.extension;

    String JAVA_FILE_EXTENSION = JavaFileObject.Kind.SOURCE.extension;


}
