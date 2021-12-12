package com.fengjx.reload.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StrUtilsTest {

    @Test
    public void testJoin() {
        List<Object> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String res = StrUtils.join(list, ",");
        Assert.assertEquals(res, "a,b,c");
    }


}
