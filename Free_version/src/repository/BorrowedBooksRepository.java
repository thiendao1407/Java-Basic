package repository;

import static repository.MySQLDatabaseConfiguration.PASSWORD;
import static repository.MySQLDatabaseConfiguration.URL;
import static repository.MySQLDatabaseConfiguration.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import entity.Book;
import entity.BorrowedBooks;
import exception.BookNotFoundException;

public class BorrowedBooksRepository {
	public int getNumberOfBorowedBooks(String book_id) throws SQLException {
		final String sql = "SELECT SUM(number_of_borrowed_books) AS number_of_borrowed_books FROM borrowedbooks WHERE book_id = ?";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, book_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					return rs.getInt("number_of_borrowed_books");

				} else
					return 0;
			}
		}

	}

	public int getNumberOfRemainingBooks(String book_id) throws SQLException, BookNotFoundException {
		final String sql = "SELECT b.book_id, number_of_books - IFNULL(SUM(number_of_borrowed_books), 0) AS remaining_books"
				+ " FROM book AS b LEFT JOIN borrowedbooks AS br ON b.book_id = br.book_id "
				+ "WHERE b.book_id = ? GROUP BY b.book_id;";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, book_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					return rs.getInt("remaining_books");
				} else
					throw new BookNotFoundException(book_id);
			}
		}

	}

	public int getNumberOfBorowedBooks(String book_id, String user_name) throws SQLException {
		final String sql = "SELECT number_of_borrowed_books FROM borrowedbooks WHERE book_id = ? AND user_name = ?";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, book_id);
			ps.setString(2, user_name);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					return rs.getInt("number_of_borrowed_books");
				} else
					return 0;
			}
		}

	}

	public LinkedHashMap<Book, Integer> mapBookToItsRemaining() throws SQLException {
		LinkedHashMap<Book, Integer> map = new LinkedHashMap<Book, Integer>();

		final String sql = "SELECT b.*, number_of_books - IFNULL(SUM(number_of_borrowed_books), 0) AS remaining_books "
				+ "FROM book AS b LEFT JOIN borrowedbooks AS br " + "ON b.book_id = br.book_id " + "GROUP BY b.book_id";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Book book = mapResultSetToBook(rs);
					Integer remaining_books = rs.getInt("remaining_books");
					map.put(book, remaining_books);
				}
			}
		}

		return map;

	}

	private Book mapResultSetToBook(ResultSet rs) throws SQLException {
		String book_id = rs.getString("book_id");
		String book_name = rs.getString("book_name");
		String author = rs.getString("author");
		int publish_year = rs.getInt("publish_year");
		int number_of_books = rs.getInt("number_of_books");
		Book book = new Book(book_id, book_name, author, publish_year, number_of_books);
		return book;
	}

	public LinkedHashSet<BorrowedBooks> getAllBorrowedBooks(String user_name) throws SQLException {

		LinkedHashSet<BorrowedBooks> setOfBooks = new LinkedHashSet<BorrowedBooks>();

		final String sql = "SELECT book_id, number_of_borrowed_books FROM borrowedbooks WHERE user_name = ?";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, user_name);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					BorrowedBooks borrowedBooks = mapResultSetToBorrowedBook(rs, user_name);
					setOfBooks.add(borrowedBooks);
				}
			}
		}

		return setOfBooks;
	}

	private BorrowedBooks mapResultSetToBorrowedBook(ResultSet rs, String user_name) throws SQLException {
		String book_id = rs.getString("book_id");
		int number_of_borrowed_books = rs.getInt("number_of_borrowed_books");
		BorrowedBooks borrowedBooks = new BorrowedBooks(book_id, user_name, number_of_borrowed_books);
		return borrowedBooks;
	}

	public void updateRecord(String user_name, String book_id, int numberOfBorrowedBooks) throws SQLException {
		final String sql = "UPDATE borrowedbooks SET number_of_borrowed_books = number_of_borrowed_books + ? WHERE book_id = ? and user_name = ?";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, numberOfBorrowedBooks);
			ps.setString(2, book_id);
			ps.setString(3, user_name);
			ps.executeUpdate();

		}
		System.out.println("\nRecord updated sucessfully.");
	}

	public void deleteRecord(String book_id, String user_name) throws SQLException {

		final String sql = "DELETE FROM borrowedbooks WHERE book_id = ? and user_name = ?";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, book_id);
			ps.setString(2, user_name);
			ps.executeUpdate();

		}
		System.out.println("\nRecord deleted sucessfully.");
	}

}
