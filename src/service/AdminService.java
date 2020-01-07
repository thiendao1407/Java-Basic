package service;

import static service.LoginScreen.bufferedReader;

import java.io.IOException;
import java.sql.SQLException;

import entity.Book;
import exception.BookAlreadyExistsException;
import exception.BookNotFoundException;
import exception.InvalidFormatException;
import repository.BookRepository;

public class AdminService {

	public void showMenu() {

		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Insert book" + "\n2. Edit book" + "\n3. Delete book"
					+ "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
			switch (yourChoice) {
			case 0:
				break;

			case 1:
				insertBook();
				yourChoice = -1;
				break;

			case 2:
				updateBook();
				yourChoice = -1;
				break;

			case 3:
				deleteBook();
				yourChoice = -1;
				break;

			default:
				System.out.println("\nPlease choose the correct options.");

			}
		}

		return;
	}

	public void insertBook() {

		BookRepository bookRepository = new BookRepository();
		Book book = new Book();

		try {
			// Print all of the books

			book.showAllBooks(book.getAllBooks());

			System.out.println("ID of book you want to insert: ");
			String book_id = bufferedReader.readLine();

			if (bookRepository.existBookId(book_id)) {
				throw new BookAlreadyExistsException(book_id);
			}

			book = book.fillInTheInformation(book_id);
			book.checkTheValidityOfBook();

			// Insert record
			bookRepository.insertBook(book);

		} catch (IOException | BookAlreadyExistsException | InvalidFormatException | SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void updateBook() {
		BookRepository bookRepository = new BookRepository();
		Book book = new Book();

		try {
			// Print all of the books
			book.showAllBooks(book.getAllBooks());

			System.out.println("ID of book you want to update: ");
			String book_id = bufferedReader.readLine();

			if (!bookRepository.existBookId(book_id)) {
				throw new BookNotFoundException(book_id);
			}

			book = book.fillInTheInformation();
			book.checkTheValidityOfBook();

			// Update record
			bookRepository.updateBook(book, book_id);

		} catch (IOException | BookNotFoundException | InvalidFormatException | SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void deleteBook() {

		BookRepository bookRepository = new BookRepository();
		Book book = new Book();

		try {
			// Print all of the books
			book.showAllBooks(book.getAllBooks());

			System.out.println("ID of book you want to delete: ");
			String book_id = bufferedReader.readLine();

			if (!bookRepository.existBookId(book_id)) {
				throw new BookNotFoundException(book_id);
			}

			// Delete record
			bookRepository.deleteBook(book_id);

		} catch (IOException | BookNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
	}

}
