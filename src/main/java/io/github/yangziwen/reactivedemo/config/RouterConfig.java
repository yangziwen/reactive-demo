package io.github.yangziwen.reactivedemo.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.github.yangziwen.reactivedemo.handler.TimeHandler;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> timeRouter(@Autowired TimeHandler timeHandler) {
        return route(GET("/time"), request -> timeHandler.getTime(request));
    }

}
