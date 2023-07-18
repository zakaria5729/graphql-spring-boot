package com.example.graphqldemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.graphqldemo.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
