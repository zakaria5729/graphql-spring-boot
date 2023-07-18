package com.example.graphqldemo.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.graphqldemo.entity.Book;
import com.example.graphqldemo.model.BookFilterInput;
import com.example.graphqldemo.model.CreateBookInput;
import com.example.graphqldemo.repository.BookRepository;
import com.example.graphqldemo.utils.Utils;

import graphql.schema.DataFetchingEnvironment;

@Service
public class BookService {

	private final JdbcTemplate jdbcTemplate;
	private final AuthorService authorService;
	private final BookRepository bookRepository;

	public BookService(JdbcTemplate jdbcTemplate, AuthorService authorService, BookRepository bookRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.authorService = authorService;
		this.bookRepository = bookRepository;
	}

	public List<Book> allBooks(DataFetchingEnvironment environment, BookFilterInput filter) {

		final List<String> fields = environment.getSelectionSet().getFields().stream().map(field -> field.getName())
				.map(field -> Utils.getSqlColumnName(Book.class, field)).filter(field -> field != null)
				.collect(Collectors.toList());

		final String selectClause = String.join(", ", fields);

		final StringBuilder query = new StringBuilder("SELECT ");
		query.append(selectClause);
		query.append(" FROM book");

		Class<?> inputClass = BookFilterInput.class;
		Field[] filterFields = inputClass.getDeclaredFields();

		for (Field field : filterFields) {
			field.setAccessible(true); // To access private fields if needed
			String fieldName = field.getName();

			try {
				Object fieldValue = field.get(filter);

				if (fieldValue != null) {
					if (query.toString().contains("WHERE")) {
						query.append(" AND ");
					} else {
						query.append(" WHERE ");
					}

					String sqlColumnName = Utils.getSqlColumnName(Book.class, fieldName);

					if (sqlColumnName != null) {
						if (fieldValue instanceof String && !((String) fieldValue).isEmpty()) {
							query.append(sqlColumnName);
							query.append(" LIKE ");
							query.append("'%" + fieldValue.toString() + "%'");

						} else {
							query.append(sqlColumnName);
							query.append(" = ");
							query.append(fieldValue.toString());
						}
					}
				}

			} catch (IllegalAccessException e) {
				e.printStackTrace();
				System.out.println("err1: " + e);
			}
		}

		return jdbcTemplate.query(query.toString(), BeanPropertyRowMapper.newInstance(Book.class));
	}

	public Book createBook(CreateBookInput input) throws Exception {
		Book book = new Book();
		book.setPages(input.getPages());
		book.setTitle(input.getTitle());
		
		if (!authorService.existsById(input.getAuthorId())) {
			throw new Exception("Invalid authorId");
		}
		
		book.setAuthorId(input.getAuthorId());
		return bookRepository.save(book);
	}
}
