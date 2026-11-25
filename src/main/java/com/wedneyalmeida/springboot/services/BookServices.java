package com.wedneyalmeida.springboot.services;

import com.wedneyalmeida.springboot.data.dto.v1.BookDTO;
import com.wedneyalmeida.springboot.data.dto.v1.PersonDTO;
import com.wedneyalmeida.springboot.data.dto.v2.PersonDTOV2;
import com.wedneyalmeida.springboot.exception.ResourceNotFoundException;
import com.wedneyalmeida.springboot.model.Book;
import com.wedneyalmeida.springboot.model.Person;
import com.wedneyalmeida.springboot.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.wedneyalmeida.springboot.mapper.ObjectMapper.parseListObjects;
import static com.wedneyalmeida.springboot.mapper.ObjectMapper.parseObject;


@Service
public class BookServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    public Page<BookDTO> findAll(Pageable pageable){

        logger.info("Finding all Books");

        var books = repository.findAll(pageable);

        var booksDTO = books.map(book -> {
            var dto = parseObject(book, BookDTO.class);
            return dto;
        });

        return booksDTO;
    }

    public BookDTO findById(Long id){

        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id"));
        return parseObject(entity, BookDTO.class);
    }

    public BookDTO create(BookDTO book){

        logger.info("Creating one Book");
        var entity = parseObject(book, Book.class);

        return parseObject(repository.save(entity), BookDTO.class);
    }

    public BookDTO update(BookDTO book){

        logger.info("Updating one Book");
        Book entity = repository.findById(book.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        return parseObject(repository.save(entity), BookDTO.class);
    }

    public void delete(Long id){

        logger.info("Deleting one Book!");
        Book entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}
