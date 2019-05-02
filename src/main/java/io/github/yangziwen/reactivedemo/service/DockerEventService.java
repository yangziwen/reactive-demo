package io.github.yangziwen.reactivedemo.service;

import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.EventsResultCallback;

import reactor.core.publisher.Flux;

@Service
public class DockerEventService {

    public Flux<Event> collectDockerEvents() {
        DockerClient docker = DockerClientBuilder.getInstance().build();
        return Flux.create(sink -> {
            docker.eventsCmd().exec(new EventsResultCallback() {
                @Override
                public void onNext(Event event) {
                    sink.next(event);
                }
            });
        });
    }

}
