package io.github.yangziwen.reactivedemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.EventsResultCallback;

import io.github.yangziwen.reactivedemo.model.DockerEvent;
import io.github.yangziwen.reactivedemo.repo.DockerEventRepository;
import reactor.core.publisher.Flux;

@Service
public class DockerEventService {


    @Autowired
    private DockerEventRepository dockerEventRepository;

    public Flux<DockerEvent> collectDockerEvents() {
        DockerClient docker = DockerClientBuilder.getInstance().build();
        return Flux.<Event>create(sink -> {
            docker.eventsCmd().exec(new EventsResultCallback() {
                @Override
                public void onNext(Event event) {
                    sink.next(event);
                }
            });
        }).map(DockerEvent::from);
    }

    public Flux<DockerEvent> findDockerEvents() {
        return dockerEventRepository.findBy();
    }

    public Flux<DockerEvent> findDockerEventsByStatus(String status) {
        return dockerEventRepository.findByStatus(status);
    }

    public Flux<DockerEvent> findDockerEventsByTypeAndFrom(String type, String from) {
        return dockerEventRepository.findByTypeAndFrom(type, from);
    }

}
