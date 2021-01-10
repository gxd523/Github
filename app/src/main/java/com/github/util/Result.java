package com.github.util;

import javax.annotation.Nullable;

// TODO: 1/9/21 重点：骚操作，利用平台类型不用判空
public class Result<T> {
    private T value;
    private Throwable error;

    public static <T> Result<T> of(Throwable error) {
        Result<T> result = new Result<>();
        result.error = error;
        return result;
    }

    public static <T> Result<T> of(T value) {
        Result<T> result = new Result<>();
        result.value = value;
        return result;
    }

    public T getValue() {
        return value;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public T component1() {
        return value;
    }

    @Nullable // TODO: 1/9/21 重点：加Nullable，在Kotlin中为可空类型
    public Throwable component2() {
        return error;
    }
}
