package controller;

import java.util.LinkedHashSet;

import book.Book;

public interface SearchBook {
	public LinkedHashSet<Book> searchBookByTitle();

	public LinkedHashSet<Book> searchBookByAuthor();

	public LinkedHashSet<Book> searchBookBySubject();
}
