package controller;

import static view.Main.bufferedReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import book.Book;
import book.Book.BookStatus;
import book.Book.BookSubject;
import book.BookFactory;
import exception.ValueAlreadyExistsException;
import exception.ValueNotFoundException;
import repository.BookRepository;

public class BookControl implements SearchBook {

	BookRepository bookRepository = new BookRepository();

	public void addBook() {
		try {
			Book book = BookFactory.retrieveBook(getBookInstance());
			System.out.println("ID of book you want to insert: ");
			String book_id = bufferedReader.readLine();
			if (bookRepository.existBookId(book_id)) {
				throw new ValueAlreadyExistsException("This ID: " + book_id + " already exists");
			}

			setBookInformation(book, book_id);
			bookRepository.addBook(book);

		} catch (IOException | ValueAlreadyExistsException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void editBook() {

		try {
			System.out.println("ID of book you want to edit: ");
			String oldBook_id = bufferedReader.readLine();

			if (!bookRepository.existBookId(oldBook_id)) {
				throw new ValueNotFoundException("This ID: " + oldBook_id + " does not exist.");
			}

			System.out.println("New ID of book: ");
			String newBook_id = bufferedReader.readLine();
			if (bookRepository.existBookId(newBook_id)) {
				throw new ValueAlreadyExistsException("This ID: " + newBook_id + " already exists");
			}

			Book book = BookFactory.retrieveBook(getBookInstance());
			setBookInformation(book, newBook_id);
			bookRepository.editBook(book, oldBook_id);

		} catch (IOException | ValueNotFoundException | SQLException | ValueAlreadyExistsException e) {
			e.printStackTrace();
		}
	}

	public void deleteBook() {

		try {
			System.out.println("ID of book you want to delete: ");
			String book_id = bufferedReader.readLine();

			if (!bookRepository.existBookId(book_id)) {
				throw new ValueNotFoundException(book_id);
			}

			bookRepository.deleteBook(book_id);
		} catch (IOException | ValueNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public String getBookInstance() throws NumberFormatException, IOException {
		System.out.println("Please choose the book subject" + "\n1. Science - Technology" + "\n2. Dictionary"
				+ "\n3. Literature" + "\nYour choice: ");

		int yourChoice = Integer.parseInt(bufferedReader.readLine());
		switch (yourChoice) {
		case 1:
			return "Science - Technology";
		case 2:
			return "Dictionary";
		case 3:
			return "Literature";
		default:
			throw new IllegalArgumentException("\nPlease choose the correct options.");
		}

	}

	@Override
	public LinkedHashSet<Book> searchBookByTitle() {
		System.out.println("Please enter title (No full name required): ");
		try {
			String regex = "%" + bufferedReader.readLine().toLowerCase() + "%";
			LinkedHashSet<Book> resultSet = bookRepository.searchBook("title", regex, false);
			// Print the result
			showBooks(resultSet);
			return resultSet;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedHashSet<Book> searchBookByAuthor() {
		System.out.println("Please enter author (No full name required): ");
		try {
			String regex = "%" + bufferedReader.readLine().toLowerCase() + "%";
			LinkedHashSet<Book> resultSet = bookRepository.searchBook("author", regex, false);
			// Print the result
			showBooks(resultSet);
			return resultSet;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedHashSet<Book> searchBookBySubject() {
		System.out.println("\nPlease choose subject\n1. Dictionary\n2. Science and Technology\n3. Literature");
		String book_subject;
		try {
			int yourChoice = Integer.parseInt(bufferedReader.readLine());
			switch (yourChoice) {
			case 1:
				book_subject = BookSubject.DICTIONARY.toString();
				break;
			case 2:
				book_subject = BookSubject.SCIENCE_AND_TECHNOLOGY.toString();
				break;
			case 3:
				book_subject = BookSubject.LITERATURE.toString();
				break;
			default:
				throw new IllegalArgumentException("\nPlease choose the correct options.");
			}
			LinkedHashSet<Book> resultSet = bookRepository.searchBook("subject", book_subject, true);
			// Print the result
			showBooks(resultSet);
			return resultSet;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void showBooks(LinkedHashSet<Book> linkedHashSet) {
		if (linkedHashSet.isEmpty())
			return;
		System.out.println("------");
		for (Book book : linkedHashSet) {
			System.out.println(book);
		}

		System.out.println("------");
	}

	private void setBookInformation(Book book, String book_id) throws IOException {
		book.setBook_id(book_id);

		System.out.print("Title of book: ");
		book.setTitle(bufferedReader.readLine());

		System.out.print("Author: ");
		book.setAuthor(bufferedReader.readLine());

		System.out.print("Publish date(yyyy-mm-dd): ");
		book.setPublish_date(bufferedReader.readLine());

		System.out.print("Number of books: ");
		book.setNumber_of_books(Integer.parseInt(bufferedReader.readLine()));

		System.out.print("Book Status: ");
		book.setBook_status(chooseBookStatus());

	}

	private String chooseBookStatus() throws NumberFormatException, IOException {
		System.out.println("\n1. Available" + "\n2. Reserved" + "\nYour choice: ");

		int yourChoice = Integer.parseInt(bufferedReader.readLine());
		switch (yourChoice) {
		case 1:
			return BookStatus.AVAILABLE.toString();
		case 2:
			return BookStatus.RESERVED.toString();
		default:
			throw new IllegalArgumentException("\nPlease choose the correct options.");
		}

	}

}
