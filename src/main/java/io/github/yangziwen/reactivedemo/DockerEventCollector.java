package io.github.yangziwen.reactivedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

import io.github.yangziwen.reactivedemo.model.DockerEvent;
import io.github.yangziwen.reactivedemo.repo.DockerEventRepository;
import io.github.yangziwen.reactivedemo.service.DockerEventService;

@Component
public class DockerEventCollector implements CommandLineRunner {

    @Autowired
    private DockerEventService dockerEventService;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    private DockerEventRepository dockerEventRepository;

    @Override
    public void run(String... args) throws Exception {
        mongoTemplate.dropCollection(DockerEvent.class)
            .then(mongoTemplate.createCollection(
                DockerEvent.class,
                CollectionOptions.empty().maxDocuments(200).size(100000).capped()))
            .then(dockerEventRepository.save(DockerEvent.builder().build()))
            .thenMany(dockerEventRepository.saveAll(dockerEventService.collectDockerEvents()))
            .subscribe();
    }

}
