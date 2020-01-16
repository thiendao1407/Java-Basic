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
import java.util.LinkedHashSet;

import entity.Book;
import exception.BookNotFoundException;

public class BookRepository {

	public void insertBook(Book book) throws SQLException {
		final String sql = "INSERT INTO book VALUES (?, ?, ?, ?, ?)";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, book.getBook_id());
			ps.setString(2, book.getBook_name());
			ps.setString(3, book.getAuthor());
			ps.setInt(4, book.getPublish_year());
			ps.setInt(5, book.getNumber_of_books());

			ps.executeUpdate();
			System.out.println("\nBook inserted successfully.");
		}
	}

	public void updateBook(Book book, String oldBook_id) throws SQLException {
		final String sql = "UPDATE book SET book_id = ?, book_name = ?, author = ?, publish_year = ?, "
				+ "number_of_books = ? WHERE (book_id = ?)";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, book.getBook_id());
			ps.setString(2, book.getBook_name());
			ps.setString(3, book.getAuthor());
			ps.setInt(4, book.getPublish_year());
			ps.setInt(5, book.getNumber_of_books());
			ps.setString(6, oldBook_id);

			ps.executeUpdate();
			System.out.println("\nBook updated successfully.");
		}
	}

	public void deleteBook(String book_id) throws SQLException {
		final String sql = "DELETE FROM book" + " WHERE book_id = '" + book_id + "'";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement stmt = con.createStatement()) {

			stmt.executeUpdate(sql);
			System.out.println("\nBook deleted successfully.");
		}
	}

	public LinkedHashSet<Book> getAllBooksToSet() throws SQLException {

		LinkedHashSet<Book> listOfBooks = new LinkedHashSet<Book>();

		final String sql = "SELECT * FROM book";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Book book = mapResultSetToBook(rs);
					listOfBooks.add(book);
				}
			}
		}

		return listOfBooks;
	}

	public Book getBookById(String book_id) throws SQLException, BookNotFoundException {
		final String sql = "SELECT * FROM book " + "WHERE book_id = ?";
		Book book = null;

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, book_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					book = mapResultSetToBook(rs);
				} else {
					throw new BookNotFoundException(book_id);
				}
			}
		}

		return book;
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

	public boolean existBookId(String book_id) throws SQLException {

		boolean existBookId = false;

		final String sql = "SELECT book_id FROM book " + "WHERE book_id = ?";

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, book_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					existBookId = true;
				} else {
					// do nothing
				}
			}
		}

		return existBookId;
	}

	public LinkedHashSet<Book> searchBook(String bookField, String regex) throws SQLException {
		final String sql = "SELECT * FROM book " + "WHERE " + bookField + " LIKE ?";
		LinkedHashSet<Book> listOfBooks = new LinkedHashSet<Book>();

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, regex);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Book book = mapResultSetToBook(rs);
					listOfBooks.add(book);
				}
			}
		}

		return listOfBooks;
	}
}
