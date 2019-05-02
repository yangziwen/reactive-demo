package io.github.yangziwen.reactivedemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.yangziwen.reactivedemo.common.BaseResponse;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ExceptionHandleController {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<BaseResponse<String>> handleException(Exception exception) {
        return Mono.just(BaseResponse.error(exception.getMessage()));
    }

}
