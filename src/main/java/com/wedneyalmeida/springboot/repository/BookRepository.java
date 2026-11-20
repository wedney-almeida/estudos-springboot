package com.wedneyalmeida.springboot.repository;

import com.wedneyalmeida.springboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> id(Long id);
}
