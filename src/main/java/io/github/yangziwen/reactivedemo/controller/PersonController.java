package io.github.yangziwen.reactivedemo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.DeleteResult;

import io.github.yangziwen.reactivedemo.common.BaseResponse;
import io.github.yangziwen.reactivedemo.model.Person;
import io.github.yangziwen.reactivedemo.service.PersonService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/create")
    public Mono<BaseResponse<Person>> createPerson(Person person) {
        return personService.insertPerson(Mono.just(person))
                .map(BaseResponse::success)
                .onErrorResume(error -> Mono.just(BaseResponse.error("failed to create person %s", person)));
    }

    @GetMapping("/remove")
    public Mono<BaseResponse<Long>> removePerson(String id) {
        Person person = Person.builder().id(id).build();
        return personService.removePerson(Mono.just(person))
                .map(DeleteResult::getDeletedCount)
                .map(BaseResponse::success);
    }

    @GetMapping("/findAll")
    public Mono<BaseResponse<List<Person>>> findAll() {
        return personService.findAll()
                .buffer()
                .next()
                .defaultIfEmpty(Collections.emptyList())
                .map(BaseResponse::success);
    }

    @GetMapping("/findByName")
    public Mono<BaseResponse<List<Person>>> findByName(@RequestParam String name) {
        return personService.findPersonByName(name)
                .buffer()
                .next()
                .defaultIfEmpty(Collections.emptyList())
                .map(BaseResponse::success);
    }

}
