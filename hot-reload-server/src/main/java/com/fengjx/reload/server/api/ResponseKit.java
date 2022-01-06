package com.fengjx.reload.server.api;

import com.fengjx.reload.common.proto.Result;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengjianxin
 */
@UtilityClass
public class ResponseKit {


    public static Result<Map<String, Object>> ok() {
        return ok(new HashMap<>());
    }

    public static <T> Result<T> ok(T data) {
        Result<T> res = new Result<>();
        res.setCode(200);
        res.setMsg("ok");
        res.setData(data);
        return res;
    }

    public static Result<Map<String, Object>> fail() {
        return fail(new HashMap<>());
    }

    public static <T> Result<T> fail(T data) {
        Result<T> res = new Result<>();
        res.setCode(500);
        res.setMsg("ok");
        res.setData(data);
        return res;
    }

}
