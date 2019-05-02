package io.github.yangziwen.reactivedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.model.Event;

import io.github.yangziwen.reactivedemo.service.DockerEventService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/docker")
public class DockerEventController {

    @Autowired
    private DockerEventService dockerEventService;

    @GetMapping(path = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> getDockerEventStream() {
        return dockerEventService.collectDockerEvents();
    }

}
