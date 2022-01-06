package com.fengjx.reload.common.utils;

import com.fengjx.reload.common.jvm.ClassUtils;
import org.junit.Assert;
import org.junit.Test;

public class ClassUtilsTest {


    @Test
    public void testClassNameToPath() {
        String path = ClassUtils.classNameToPath("/temp/", "com.fengjx.test.Hello", true);
        Assert.assertEquals("/temp/com/fengjx/test/Hello.class", path);
    }


}
