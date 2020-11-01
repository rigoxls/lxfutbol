package com.lxfutbol.provider.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`operation`")
public class OperationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(columnDefinition = "TEXT")
	private String search;
	
	@Column(columnDefinition = "TEXT")
	private String book;
	
	@Column(name = "cancel_book", columnDefinition = "TEXT")	
	private String cancelBook;
	
	protected OperationEntity() {}
			
	public OperationEntity(String search, String book, String cancelBook) {
		super();		
		this.search = search;
		this.book = book;
		this.cancelBook = cancelBook;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getBook() {
		return book;
	}
	
	public void setBook(String book) {
		this.book = book;
	}
	
	public String getCancelBook() {
		return cancelBook;
	}
	
	public void setCancelBook(String cancelBook) {
		this.cancelBook = cancelBook;
	}
	
}
