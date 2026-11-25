package com.wedneyalmeida.springboot.controllers;

import com.wedneyalmeida.springboot.controllers.docs.BookControllerDocs;
import com.wedneyalmeida.springboot.data.dto.v1.BookDTO;
import com.wedneyalmeida.springboot.data.dto.v1.PersonDTO;
import com.wedneyalmeida.springboot.model.Book;
import com.wedneyalmeida.springboot.services.BookServices;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for Mapping Books")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)

    @Override
    public ResponseEntity<Page<BookDTO>> findByAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC ;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    @Override
    public BookDTO findById(@PathVariable("id") Long id){
        var book = service.findById(id);
        book.setLaunchDate(new Date());
        book.setSensitiveData("Foo Bar");
        return book;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    @Override
    public BookDTO create(@RequestBody BookDTO book){
        return service.create(book);
    }

    @PostMapping(value = "/v1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )


    @Override
    public BookDTO update(@RequestBody BookDTO book){

        return service.update(book);
    }

    @DeleteMapping(value = "/{id}")

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
