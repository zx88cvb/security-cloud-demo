package com.security.securityclouddemo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private int code = 0;

    private String msg = "success";

    private T data;

    public Result() {
    }

    public Result(T data, String msg) {
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> ok() {
        return new Result<T>(0, null, null);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(0, data, null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(0, data, msg);
    }

    public static <T> Result<T> failed() {
        return restResult(-1,null,null);
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(-1,null, msg);
    }

    private static <T> Result<T> restResult(int code, T data, String msg) {
        Result<T> apiResult = new Result<T>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
