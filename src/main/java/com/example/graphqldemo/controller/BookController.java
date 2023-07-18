package com.example.graphqldemo.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.graphqldemo.entity.Book;
import com.example.graphqldemo.model.BookFilterInput;
import com.example.graphqldemo.model.CreateBookInput;
import com.example.graphqldemo.service.BookService;

import graphql.schema.DataFetchingEnvironment;

@Controller
public class BookController {

	private BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@QueryMapping
	public List<Book> allBooks(DataFetchingEnvironment environment, @Argument BookFilterInput filter) {
		return bookService.allBooks(environment, filter);
	}

	@MutationMapping
	public Book createBook(@Argument CreateBookInput input) throws Exception {
		return bookService.createBook(input);
	}
}