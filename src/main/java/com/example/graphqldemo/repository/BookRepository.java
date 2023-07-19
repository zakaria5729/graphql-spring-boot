package com.example.graphqldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.graphqldemo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
