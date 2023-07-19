package com.example.graphqldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.graphqldemo.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
