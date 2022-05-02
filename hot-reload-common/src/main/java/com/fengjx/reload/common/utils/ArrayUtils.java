package com.fengjx.reload.common.utils;

/**
 * @author FengJianxin
 * @since 2022/5/1
 */
public class ArrayUtils {

    public static Object[] sliceOfArray(Object[] arr, int start, int end) {
        Object[] slice = new Object[end - start];
        System.arraycopy(arr, start, slice, 0, slice.length);
        return slice;
    }

    public static String[] sliceOfArray(String[] arr, int start, int end) {
        String[] slice = new String[end - start];
        System.arraycopy(arr, start, slice, 0, slice.length);
        return slice;
    }

    public static String[] sliceOfArray(String[] arr, int start) {
        int end = arr.length;
        return sliceOfArray(arr, start, end);
    }

    public static int[] sliceOfArray(int[] arr, int start, int end) {
        int[] slice = new int[end - start];
        System.arraycopy(arr, start, slice, 0, slice.length);
        return slice;
    }

}
