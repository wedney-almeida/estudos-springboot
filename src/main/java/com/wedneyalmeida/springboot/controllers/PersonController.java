package com.wedneyalmeida.springboot.controllers;

import com.wedneyalmeida.springboot.controllers.docs.PersonControllerDocs;
import com.wedneyalmeida.springboot.data.dto.v1.PersonDTO;
import com.wedneyalmeida.springboot.data.dto.v2.PersonDTOV2;
import com.wedneyalmeida.springboot.services.PersonServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Enpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)

    @Override
    public ResponseEntity<Page<PersonDTO>> findByAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC ;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    //@CrossOrigin(origins = "http://localhost:8080")
    @Override
    public PersonDTO findById(@PathVariable("id") Long id){
        var person = service.findById(id);
        person.setBirthDate(new Date());
        person.setPhoneNumber("+55 (33) 9999-9999");
        person.setSensitiveData("Foo Bar");
        return person;
    }
    //@CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    @Override
    public PersonDTO create(@RequestBody PersonDTO person){
        return service.create(person);
    }

    @PostMapping(value = "/v2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    @Override
    public PersonDTOV2 create(@RequestBody PersonDTOV2 person){
        return service.createV2(person);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    @Override
    public PersonDTO update(@RequestBody PersonDTO person){

        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
