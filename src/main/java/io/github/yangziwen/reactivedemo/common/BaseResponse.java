package io.github.yangziwen.reactivedemo.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseResponse<T> {

    private int code;

    private String message;

    private T data;

    public static <R> BaseResponse<R> success(R data) {
        return BaseResponse.<R>builder()
                .code(0)
                .data(data)
                .build();
    }

    public static <R> BaseResponse<R> error(String message, Object...args) {
        return BaseResponse.<R>builder()
                .code(-1)
                .message(String.format(message, args))
                .build();
    }

}
