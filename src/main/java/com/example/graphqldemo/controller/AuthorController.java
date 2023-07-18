package com.example.graphqldemo.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.graphqldemo.entity.Author;
import com.example.graphqldemo.model.CreateAuthorInput;
import com.example.graphqldemo.service.AuthorService;

@Controller
public class AuthorController {

	private AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@QueryMapping
	public List<Author> allAuthors() {
		return authorService.allAuthors();
	}

	@MutationMapping
	public Author createAuthor(@Argument CreateAuthorInput input) throws Exception {
		return authorService.createAuthor(input);
	}

}