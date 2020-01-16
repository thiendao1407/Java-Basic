package entity;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import repository.BorrowedBooksRepository;

public class BorrowedBooks {
	private String book_id;
	private String user_name;
	private int number_of_borrowed_books;

	public BorrowedBooks() {
		//
	}

	public BorrowedBooks(String book_id, String user_name, int number_of_borrowed_books) {
		this.book_id = book_id;
		this.user_name = user_name;
		this.number_of_borrowed_books = number_of_borrowed_books;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public int getNumber_of_borrowed_books() {
		return number_of_borrowed_books;
	}

	public void setNumber_of_borrowed_books(int number_of_borrowed_books) {
		this.number_of_borrowed_books = number_of_borrowed_books;
	}

	public String toString() {
		return "ID: " + this.book_id + ", Username: " + this.user_name + ", Number of borrowed books: "
				+ this.number_of_borrowed_books;
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
