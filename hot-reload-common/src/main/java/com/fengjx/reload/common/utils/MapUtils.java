package com.fengjx.reload.common.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * @author FengJianxin
 * @since 2022/3/27
 */
@UtilityClass
public class MapUtils {

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }


}
