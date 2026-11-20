package com.wedneyalmeida.springboot.mapper.custom;

import com.wedneyalmeida.springboot.data.dto.v1.BookDTO;
import com.wedneyalmeida.springboot.model.Book;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookMapper {

    public BookDTO convertEntityToDTO(Book book){
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setAuthor(book.getAuthor());
        dto.setLaunchDate(new Date());
        dto.setPrice(book.getPrice());
        dto.setTitle(book.getTitle());
        return dto;
    }

    public BookDTO convertDTOToEntity(Book book){
        BookDTO entity = new BookDTO();
        entity.setId(book.getId());
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(new Date());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
        return entity;
    }
}
