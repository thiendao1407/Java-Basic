package service;

import static service.LoginScreen.bufferedReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import entity.Book;
import entity.BorrowedBooks;
import exception.BookNotFoundException;
import repository.BookRepository;
import repository.BorrowedBooksRepository;

public class ReaderService {
	public void showMenu(String user_name) {

		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Search books" + "\n2. Borrow books" + "\n3. Return books"
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
				showSearchMenu();
				yourChoice = -1;
				break;

			case 2:
				borrowBook(user_name);
				yourChoice = -1;
				break;

			case 3:
				returnBook(user_name);
				yourChoice = -1;
				break;

			default:
				System.out.println("\nPlease choose the correct options.");
			}

		}

		return;
	}

	public void showSearchMenu() {
		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Search by ID" + "\n2. Search by name"
					+ "\n3. Search by author" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
			switch (yourChoice) {
			case 0:
				break;

			case 1:
				searchBook("book_id");
				yourChoice = -1;
				break;

			case 2:
				searchBook("book_name");
				yourChoice = -1;
				break;

			case 3:
				searchBook("author");
				yourChoice = -1;
				break;

			default:
				System.out.println("\nPlease choose the correct options.");
			}

		}

		return;

	}

	public LinkedHashSet<Book> searchBook(String bookField) {

		System.out.println("Please enter keyword (No full name required): ");
		try {
			String regex = "%" + bufferedReader.readLine() + "%";

			BookRepository br = new BookRepository();
			LinkedHashSet<Book> resultSet = br.searchBook(bookField, regex);

			// Print the result
			new AdminService().showAllBooks(resultSet);

			return resultSet;

		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void borrowBook(String user_name) {
		// Print the Map
		showMap(mapBookToItsRemaining());

		System.out.println("ID of book you want to borrow: ");
		try {
			String book_id = bufferedReader.readLine();

			BorrowedBooksRepository bbr = new BorrowedBooksRepository();
			int remainingBooks = bbr.getNumberOfRemainingBooks(book_id);

			System.out.println("How many books do you want to borrow? ");
			int yourNumber = Integer.parseInt(bufferedReader.readLine());

			if (yourNumber > remainingBooks || yourNumber <= 0) {
				System.out.println("\nThe number is not valid!...");
				return;
			}

			// Update record
			bbr.updateRecord(user_name, book_id, yourNumber);

		} catch (IOException | SQLException | BookNotFoundException e) {
			e.printStackTrace();
			return;
		}

		return;

	}

	public void returnBook(String user_name) {

		// Print the Map
		showAllBorrowedBooks(getAllBorrowedBooks(user_name));

		System.out.println("ID of book you want to return: ");
		try {
			String book_id = bufferedReader.readLine();

			BorrowedBooksRepository bbr = new BorrowedBooksRepository();
			int booksYouBorrowed = bbr.getNumberOfBorowedBooks(book_id, user_name);
			if (booksYouBorrowed == 0) {
				System.out.println("\nYou do not borrow this book");
				return;
			}

			System.out.println("How many books do you want to return? ");
			int yourNumber = Integer.parseInt(bufferedReader.readLine());

			if (yourNumber > booksYouBorrowed || yourNumber <= 0) {
				System.out.println("\nThe number is not valid!...");
				return;
			}

			// Update the table
			if (booksYouBorrowed - yourNumber > 0) {
				bbr.updateRecord(user_name, book_id, -yourNumber);
			} else {
				bbr.deleteRecord(book_id, user_name);
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public LinkedHashMap<Book, Integer> mapBookToItsRemaining() {
		BorrowedBooksRepository bbr = new BorrowedBooksRepository();
		try {
			return bbr.mapBookToItsRemaining();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void showMap(LinkedHashMap<Book, Integer> linkedHashMap) {
		System.out.println("------");
		Set<Book> keySet = linkedHashMap.keySet();
		for (Book key : keySet) {
			System.out.println(key + " remaining: " + linkedHashMap.get(key));
		}
		System.out.println("------");
	}

	public void showAllBorrowedBooks(LinkedHashSet<BorrowedBooks> linkedHashSet) {
		System.out.println("------");
		for (BorrowedBooks borrowedBook : linkedHashSet) {
			System.out.println(borrowedBook);
		}
		System.out.println("------");

	}

	public LinkedHashSet<BorrowedBooks> getAllBorrowedBooks(String user_name) {
		BorrowedBooksRepository bbr = new BorrowedBooksRepository();
		try {
			return bbr.getAllBorrowedBooks(user_name);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
