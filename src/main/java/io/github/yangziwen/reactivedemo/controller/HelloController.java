package io.github.yangziwen.reactivedemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.yangziwen.reactivedemo.common.BaseResponse;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<BaseResponse<String>> hello(@RequestParam String name) {
        return Mono.just(BaseResponse.success("hello " + name));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<BaseResponse<String>> handleException(Exception exception) {
        return Mono.just(BaseResponse.error(exception.getMessage()));
    }

}
