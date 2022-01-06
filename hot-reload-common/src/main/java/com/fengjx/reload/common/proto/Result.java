package com.fengjx.reload.common.proto;

import lombok.Getter;

/**
 * @author fengjianxin
 */
@Getter
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
