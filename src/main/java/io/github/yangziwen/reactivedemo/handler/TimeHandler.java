package io.github.yangziwen.reactivedemo.handler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.github.yangziwen.reactivedemo.common.BaseResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TimeHandler {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Mono<ServerResponse> getTime(ServerRequest request) {
        BaseResponse<String> response = BaseResponse.success(LocalDateTime.now().format(formatter));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(response), BaseResponse.class);
    }

    public Mono<ServerResponse> getRealtime(ServerRequest request) {
        Flux<String> flux = Flux.interval(Duration.ofSeconds(1L))
                .map(v -> LocalDateTime.now().format(formatter));
        return ServerResponse.ok()
                .contentType((MediaType.TEXT_EVENT_STREAM))
                .body(flux, String.class);
    }

}
