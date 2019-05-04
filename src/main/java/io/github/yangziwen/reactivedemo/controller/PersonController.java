package io.github.yangziwen.reactivedemo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/create")
    public Mono<BaseResponse<Person>> createPerson(Person person) {
        BaseResponse<Person> errorResponse = BaseResponse
                .error("failed to create person %s", person);
        return personService.insertPerson(Mono.just(person))
                .map(BaseResponse::success)
                .onErrorResume(error -> Mono.just(errorResponse));
    }

    @RequestMapping("/remove")
    public Mono<BaseResponse<Long>> removePerson(String id) {
        Person person = Person.builder().id(id).build();
        return personService.removePerson(Mono.just(person))
                .map(DeleteResult::getDeletedCount)
                .map(BaseResponse::success);
    }

    @RequestMapping("/findAll")
    public Mono<BaseResponse<List<Person>>> findAll() {
        return personService.findAllPerson()
                .buffer()
                .next()
                .defaultIfEmpty(Collections.emptyList())
                .map(BaseResponse::success);
    }

    @RequestMapping("/findByName")
    public Mono<BaseResponse<List<Person>>> findByName(
            @RequestParam String name) {
        return personService.findPersonByName(name)
                .buffer()
                .next()
                .defaultIfEmpty(Collections.emptyList())
                .map(BaseResponse::success);
    }

}
