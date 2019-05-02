package io.github.yangziwen.reactivedemo.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import io.github.yangziwen.reactivedemo.model.DockerEvent;
import reactor.core.publisher.Flux;

public interface DockerEventRepository extends ReactiveMongoRepository<DockerEvent, String> {

    @Tailable
    Flux<DockerEvent> findBy();

    @Tailable
    Flux<DockerEvent> findByStatus(String status);

    @Tailable
    Flux<DockerEvent> findByTypeAndFrom(String type, String from);

}
