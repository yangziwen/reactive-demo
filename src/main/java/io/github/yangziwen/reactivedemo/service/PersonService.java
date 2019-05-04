package io.github.yangziwen.reactivedemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;

import io.github.yangziwen.reactivedemo.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public Mono<Person> insertPerson(Mono<Person> person) {
        return mongoTemplate.insert(person);
    }

    public Mono<DeleteResult> removePerson(Mono<Person> person) {
        return mongoTemplate.remove(person);
    }

    public Flux<Person> findPersonByName(String name) {
        Query query = Query.query(Criteria
                .where("name").regex(name + ".+"));
        return mongoTemplate.find(query, Person.class);
    }

    public Flux<Person> findAllPerson() {
        return mongoTemplate.findAll(Person.class);
    }

}
