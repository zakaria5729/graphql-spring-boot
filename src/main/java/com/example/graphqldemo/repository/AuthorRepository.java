package com.example.graphqldemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.graphqldemo.entity.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}
