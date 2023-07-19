package com.example.graphqldemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.graphqldemo.entity.Author;
import com.example.graphqldemo.entity.Book;
import com.example.graphqldemo.model.BookFilterInput;
import com.example.graphqldemo.model.CreateBookInput;
import com.example.graphqldemo.repository.BookRepository;

import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Service
public class BookService {
	
	private final EntityManager entityManager;
	private final AuthorService authorService;
	private final BookRepository bookRepository;

	public BookService(EntityManager entityManager, AuthorService authorService, BookRepository bookRepository) {
		this.entityManager = entityManager;
		this.authorService = authorService;
		this.bookRepository = bookRepository;
	}

	public List<Book> allBooks(DataFetchingEnvironment environment, BookFilterInput filter) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
	    Root<Book> root = criteriaQuery.from(Book.class);
	    
	    if (filter != null && filter.getTitle() != null && !filter.getTitle().isEmpty()) {
	        criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
	    }
	    
	    if (environment.getSelectionSet().contains("author")) {
	    	Join<Book, Author> authorJoin = root.join("author", JoinType.LEFT);
	    	
	    	if (filter != null && filter.getAuthorId() != null) {
	    	    criteriaQuery.where(criteriaBuilder.equal(authorJoin.get("id"), filter.getAuthorId()));
		    }
	    }
	    
	    criteriaQuery.select(root);
	    
	    TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
	    return typedQuery.getResultList();
	}

	public Book createBook(CreateBookInput input) throws Exception {
		Book book = new Book();
		book.setPages(input.getPages());
		book.setTitle(input.getTitle());
		
		Optional<Author> author = authorService.findById(input.getAuthorId());
		if(author.isEmpty()) {
			throw new Exception("Invalid authorId");
		}
		
		book.setAuthor(author.get());
		return bookRepository.save(book);
	}
}
