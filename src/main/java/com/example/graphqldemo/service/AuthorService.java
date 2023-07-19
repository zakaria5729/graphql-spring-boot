package com.example.graphqldemo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.example.graphqldemo.entity.Author;
import com.example.graphqldemo.model.CreateAuthorInput;
import com.example.graphqldemo.repository.AuthorRepository;

@Service
public class AuthorService {
	
	private final AuthorRepository authorRepository;
	
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	public boolean existsById(long id) {
		return authorRepository.existsById(id);
	}
	
	public Optional<Author> findById(long id) {
		return authorRepository.findById(id);
	}
	
	public List<Author> allAuthors() {
	    return StreamSupport.stream(authorRepository.findAll().spliterator(), false).toList();
	}

	public Author createAuthor(CreateAuthorInput input) {
		Author author = new Author();
		author.setName(input.getName());
		return authorRepository.save(author);
	}
}
