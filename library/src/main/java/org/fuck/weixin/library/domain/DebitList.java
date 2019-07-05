package org.fuck.weixin.library.domain;

import java.util.List;

public class DebitList {
	private List<Book> books;
	
	private List<Borrow> borrow;

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Borrow> getBorrow() {
		return borrow;
	}

	public void setBorrow(List<Borrow> borrow) {
		this.borrow = borrow;
	}
}
