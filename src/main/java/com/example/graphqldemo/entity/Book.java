package com.example.graphqldemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	private Integer pages;

	@ManyToOne(fetch = FetchType.LAZY)
	private Author author;
	
	public Book() {
		
	}

//	public Book(Long id, String title, Integer pages) {
//		this.id = id;
//		this.title = title;
//		this.pages = pages;
//	}
//	
//	public Book(Long id, String title, Integer pages, Author author) {
//		this.id = id;
//		this.title = title;
//		this.pages = pages;
//		this.author = author;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}