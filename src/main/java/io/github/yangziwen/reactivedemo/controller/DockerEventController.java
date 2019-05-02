package io.github.yangziwen.reactivedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.yangziwen.reactivedemo.model.DockerEvent;
import io.github.yangziwen.reactivedemo.service.DockerEventService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/docker")
public class DockerEventController {

    @Autowired
    private DockerEventService dockerEventService;

    @GetMapping(path = "/event0", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<DockerEvent> getDockerEventStream0() {
        return dockerEventService.collectDockerEvents();
    }

    @GetMapping(path = "/event", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<DockerEvent> getDockerEventStream() {
        return dockerEventService.findDockerEvents();
    }

    @GetMapping(path = "/event/status/{status}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<DockerEvent> getDockerEventStreamByStatus(@PathVariable String status) {
        return dockerEventService.findDockerEventsByStatus(status);
    }

    @GetMapping(path = "/event/type/{type}/from/{from}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<DockerEvent> getDockerEventStreamByTypeAndFrom(
            @PathVariable String type,
            @PathVariable String from) {
        return dockerEventService.findDockerEventsByTypeAndFrom(type, from);
    }


}
